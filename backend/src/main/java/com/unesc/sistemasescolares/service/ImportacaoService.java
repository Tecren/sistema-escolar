package com.unesc.sistemasescolares.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.model.Disciplina;
import com.unesc.sistemasescolares.model.Fase;
import com.unesc.sistemasescolares.model.Professor;
import com.unesc.sistemasescolares.util.MapeamentoDados;

@Service
public class ImportacaoService {

    private static final Logger logger = LoggerFactory.getLogger(ImportacaoService.class);

    @Autowired
    private CursoService cursoService;

    @Autowired
    private FaseService faseService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private ProfessorService professorService;

    @Transactional
    public Curso processarArquivoImportacao(MultipartFile arquivo) throws IOException {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido ou vazio");
        }

        //duplicidade
        String hashArquivo = calcularHashArquivo(arquivo);
        logger.info("Hash do arquivo: {}", hashArquivo);

        //se já foi importado
        if (cursoService.existePorHashArquivo(hashArquivo)) {
            throw new RuntimeException("Arquivo já importado anteriormente");
        }

        List<String> linhas = lerLinhasArquivo(arquivo);

        Curso curso = null;
        Fase faseAtual = null;
        Disciplina disciplinaAtual = null;

        for (String linha : linhas) {
            if (linha.isEmpty()) {
                continue;
            }

            char tipoRegistro = linha.charAt(0);

            switch (tipoRegistro) {
                case '0':
                    curso = processarRegistroHeader(linha, hashArquivo);
                    break;
                case '1':
                    if (curso != null) {
                        faseAtual = processarRegistroFase(linha, curso);
                    }
                    break;
                case '2':
                    if (faseAtual != null) {
                        disciplinaAtual = processarRegistroDisciplina(linha, faseAtual);
                    }
                    break;
                case '3':
                    if (disciplinaAtual != null) {
                        processarRegistroProfessor(linha, disciplinaAtual);
                    }
                    break;
                case '9':
                    processarRegistroTrailer(linha);
                    break;
                default:
                    logger.warn("Tipo de registro desconhecido: {}", tipoRegistro);
            }
        }

        return curso;
    }

    private Curso processarRegistroHeader(String linha, String hashArquivo) {

        String nomeCurso = linha.substring(1, 51).trim();
        String dataStr = linha.substring(51, 59).trim();
        String periodoInicial = linha.substring(59, 66).trim();
        String periodoFinal = linha.substring(66, 73).trim();
        String sequencia = linha.substring(73, 80).trim();
        String versaoLayout = linha.substring(80, 83).trim();

        LocalDate dataProcessamento = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("ddMMyyyy"));

        Curso curso = new Curso(nomeCurso, dataProcessamento, periodoInicial, periodoFinal, sequencia, versaoLayout, hashArquivo);
        return cursoService.salvar(curso);
    }

    private Fase processarRegistroFase(String linha, Curso curso) {
        
        String nomeFase = linha.substring(1, 8).trim();
        int qtdDisciplinas = Integer.parseInt(linha.substring(8, 10).trim());
        int qtdProfessores = Integer.parseInt(linha.substring(10, 12).trim());

        Fase fase = new Fase(nomeFase, qtdDisciplinas, qtdProfessores);
        fase.setCurso(curso);
        curso.adicionarFase(fase);

        return faseService.salvar(fase);
    }

    private Disciplina processarRegistroDisciplina(String linha, Fase fase) {
        
        String codigoDisciplina = linha.substring(1, 7).trim();
        String diaSemana = linha.substring(7, 9).trim();
        int qtdProfessores = Integer.parseInt(linha.substring(9, 11).trim());

        String nomeDisciplina = MapeamentoDados.getDescricaoDisciplina(codigoDisciplina);
        String nomeDiaSemana = MapeamentoDados.getDescricaoDiaSemana(diaSemana);

        Fase faseGerenciada = faseService.buscarPorId(fase.getId()) // pra tentar achgar o erro da fase não aparecendo
        .orElseThrow(() -> new RuntimeException("Fase não encontrada com id: " + fase.getId()));

        Disciplina disciplina = new Disciplina(codigoDisciplina, nomeDisciplina, diaSemana, nomeDiaSemana, qtdProfessores);

        disciplina.setFase(faseGerenciada);
        faseGerenciada.adicionarDisciplina(disciplina);

        return disciplinaService.salvar(disciplina);
    }

    private Professor processarRegistroProfessor(String linha, Disciplina disciplina) {
        
        String nomeProfessor = linha.substring(1, 41).trim();
        String tituloDocente = linha.substring(41, 43).trim();

        String nomeTituloDocente = MapeamentoDados.getDescricaoTituloDocente(tituloDocente);

        Professor professor = new Professor(nomeProfessor, tituloDocente, nomeTituloDocente);
        professor.setDisciplina(disciplina);
        disciplina.adicionarProfessor(professor);

        return professorService.salvar(professor);
    }

    private void processarRegistroTrailer(String linha) {
        int totalRegistros = Integer.parseInt(linha.substring(1, 12).trim());
        logger.info("Total de registros no arquivo: {}", totalRegistros);
    }

    private List<String> lerLinhasArquivo(MultipartFile arquivo) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        }
        return linhas;
    }

    private String calcularHashArquivo(MultipartFile arquivo) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(arquivo.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao calcular hash do arquivo", e);
        }
    }
}

