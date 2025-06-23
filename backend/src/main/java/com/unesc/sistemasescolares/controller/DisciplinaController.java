package com.unesc.sistemasescolares.controller;

import java.util.List;
import java.util.Map;
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

import com.unesc.sistemasescolares.model.Disciplina;
import com.unesc.sistemasescolares.service.DisciplinaService;
import com.unesc.sistemasescolares.service.FaseService;
import com.unesc.sistemasescolares.util.MapeamentoDados;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private FaseService faseService;

    @GetMapping("/map")
    public ResponseEntity<Map<String, String>> getDisciplinasMap() {
        return ResponseEntity.ok(MapeamentoDados.getTabelaDisciplinas());
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> buscarTodas() {
        List<Disciplina> disciplinas = disciplinaService.buscarTodas();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        Optional<Disciplina> disciplina = disciplinaService.buscarPorId(id);
        return disciplina.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fase/{faseId}")
    public ResponseEntity<List<Disciplina>> buscarPorFaseId(@PathVariable Long faseId) {
        if (!faseService.buscarPorId(faseId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Disciplina> disciplinas = disciplinaService.buscarPorFaseId(faseId);
        return ResponseEntity.ok(disciplinas);
    }

    @PostMapping
    public ResponseEntity<Disciplina> criar(@RequestBody Disciplina disciplina) {
        if (disciplina.getFase() == null || disciplina.getFase().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!faseService.buscarPorId(disciplina.getFase().getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Disciplina disciplinaCriada = disciplinaService.salvar(disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizar(@PathVariable Long id, @RequestBody Disciplina disciplina) {
        if (!disciplinaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        disciplina.setId(id);
        Disciplina disciplinaAtualizada = disciplinaService.salvar(disciplina);
        return ResponseEntity.ok(disciplinaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!disciplinaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        disciplinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
