package com.unesc.sistemasescolares;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.repository.CursoRepository;
import com.unesc.sistemasescolares.repository.DisciplinaRepository;
import com.unesc.sistemasescolares.repository.FaseRepository;
import com.unesc.sistemasescolares.repository.ProfessorRepository;
import com.unesc.sistemasescolares.service.ImportacaoService;

@SpringBootTest
public class ImportacaoServiceTest {

    @Autowired
    private ImportacaoService importacaoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FaseRepository faseRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private static final String ARQUIVO_IMPORTACAO_PATH = "src/test/resources/importacao.txt";

    @BeforeEach
    @AfterEach
    public void setup() {
        professorRepository.deleteAll();
        disciplinaRepository.deleteAll();
        faseRepository.deleteAll();
        cursoRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testImportacaoArquivo() throws IOException {
        // 1. Criar um arquivo de teste simulado
        String conteudoArquivo = "0CURSO DE CIENCIA DA COMPUTACAO        2025060701-Fase08-Fase0000001001\n" +
                                 "101-Fase0503\n" +
                                 "2108500202\n" +
                                 "3Professor Teste Um                       01\n" +
                                 "3Professor Teste Dois                      02\n" +
                                 "900000000005";

        // Garantir que o diretório existe
        Files.createDirectories(Paths.get("src/test/resources"));
        
        // Escrever o conteúdo no arquivo
        Files.write(Paths.get(ARQUIVO_IMPORTACAO_PATH), conteudoArquivo.getBytes());

        // Criar o arquivo de teste
        File arquivo = new File(ARQUIVO_IMPORTACAO_PATH);
        FileInputStream input = new FileInputStream(arquivo);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "importacao.txt", "text/plain", input);

        // 2. Chamar o serviço de importação
        Curso curso = importacaoService.processarArquivoImportacao(multipartFile);

        // 3. Verificar os dados no banco de dados
        assertNotNull(curso);
        assertEquals(1, cursoRepository.count());
        assertEquals(1, faseRepository.count());
        assertEquals(1, disciplinaRepository.count());
        assertEquals(2, professorRepository.count());

        // Verificar detalhes do curso
        assertEquals("CURSO DE CIENCIA DA COMPUTACAO", curso.getNomeCurso().trim());
        assertEquals("01-Fase", curso.getPeriodoInicial());
        assertEquals("08-Fase", curso.getPeriodoFinal());
        assertEquals("0000001", curso.getSequencia());
        assertEquals("001", curso.getVersaoLayout());
        assertNotNull(curso.getHashArquivo());

        // Verificar fase
        assertEquals(1, curso.getFases().size());
        assertEquals("01-Fase", curso.getFases().get(0).getNomeFase());
        assertEquals(5, curso.getFases().get(0).getQtdDisciplinas());
        assertEquals(3, curso.getFases().get(0).getQtdProfessores());

        // Verificar disciplina
        assertEquals(1, curso.getFases().get(0).getDisciplinas().size());
        assertEquals("10850", curso.getFases().get(0).getDisciplinas().get(0).getCodigoDisciplina());
        assertEquals("Algoritmos e Programação", curso.getFases().get(0).getDisciplinas().get(0).getNomeDisciplina());
        assertEquals("02", curso.getFases().get(0).getDisciplinas().get(0).getDiaSemana());
        assertEquals("Segunda-Feira", curso.getFases().get(0).getDisciplinas().get(0).getNomeDiaSemana());
        assertEquals(2, curso.getFases().get(0).getDisciplinas().get(0).getQtdProfessores());

        // Verificar professores
        assertEquals(2, curso.getFases().get(0).getDisciplinas().get(0).getProfessores().size());
        
        // Imprimir os dados inseridos no banco de dados
        System.out.println("=== DADOS INSERIDOS NO BANCO DE DADOS ===");
        System.out.println("CURSO: " + curso);
        curso.getFases().forEach(fase -> {
            System.out.println("  FASE: " + fase);
            fase.getDisciplinas().forEach(disciplina -> {
                System.out.println("    DISCIPLINA: " + disciplina);
                disciplina.getProfessores().forEach(professor -> {
                    System.out.println("      PROFESSOR: " + professor);
                });
            });
        });

        // Testar importação duplicada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            importacaoService.processarArquivoImportacao(multipartFile);
        });
        assertTrue(exception.getMessage().contains("já importado"));
    }
}

