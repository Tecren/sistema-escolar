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

import com.unesc.sistemasescolares.model.Professor;
import com.unesc.sistemasescolares.service.DisciplinaService;
import com.unesc.sistemasescolares.service.ProfessorService;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<List<Professor>> buscarTodos() {
        List<Professor> professores = professorService.buscarTodos();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id) {
        Optional<Professor> professor = professorService.buscarPorId(id);
        return professor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<Professor>> buscarPorDisciplinaId(@PathVariable Long disciplinaId) {
        if (!disciplinaService.buscarPorId(disciplinaId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Professor> professores = professorService.buscarPorDisciplinaId(disciplinaId);
        return ResponseEntity.ok(professores);
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        if (professor.getDisciplina() == null || professor.getDisciplina().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!disciplinaService.buscarPorId(professor.getDisciplina().getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Professor professorCriado = professorService.salvar(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professor) {
        if (!professorService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        professor.setId(id);
        Professor professorAtualizado = professorService.salvar(professor);
        return ResponseEntity.ok(professorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!professorService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        professorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

