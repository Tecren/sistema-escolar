package com.unesc.sistemasescolares.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.service.ImportacaoService;

@RestController
@RequestMapping("/api/importacao")
public class ImportacaoController {

    private static final Logger logger = LoggerFactory.getLogger(ImportacaoController.class);

    @Autowired
    private ImportacaoService importacaoService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
        Map<String, Object> response = new HashMap<>();

        if (arquivo == null || arquivo.isEmpty()) {
            response.put("sucesso", false);
            response.put("mensagem", "Arquivo inválido ou vazio");
            return ResponseEntity.badRequest().body(response);
        }

        //ver se o nome do arquivo é importacao.txt
        if (!"importacao.txt".equals(arquivo.getOriginalFilename())) {
            response.put("sucesso", false);
            response.put("mensagem", "O arquivo deve ter o nome 'importacao.txt'");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Curso curso = importacaoService.processarArquivoImportacao(arquivo);
            
            response.put("sucesso", true);
            response.put("mensagem", "Arquivo importado com sucesso");
            response.put("curso", curso);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            //arquivo duplicado
            if (e.getMessage().contains("já importado")) {
                response.put("sucesso", false);
                response.put("mensagem", "Este arquivo já foi importado anteriormente");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            logger.error("Erro ao processar o arquivo", e);
            response.put("sucesso", false);
            response.put("mensagem", "Erro ao processar o arquivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo", e);
            response.put("sucesso", false);
            response.put("mensagem", "Erro ao ler o arquivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

