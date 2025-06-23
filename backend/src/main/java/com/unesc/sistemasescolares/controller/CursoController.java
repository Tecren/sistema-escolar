package com.unesc.sistemasescolares.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.service.CursoService;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        try {
            List<Curso> cursos = cursoService.buscarTodos();
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar cursos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.buscarPorId(id);
        return curso.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> criar(@RequestBody Curso curso) {
        Curso cursoCriado = cursoService.salvar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        if (!cursoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        curso.setId(id);
        Curso cursoAtualizado = cursoService.salvar(curso);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!cursoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

