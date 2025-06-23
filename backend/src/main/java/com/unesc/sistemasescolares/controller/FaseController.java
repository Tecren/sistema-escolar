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

import com.unesc.sistemasescolares.model.Fase;
import com.unesc.sistemasescolares.service.CursoService;
import com.unesc.sistemasescolares.service.FaseService;

@RestController
@RequestMapping("/api/fases")
public class FaseController {

    @Autowired
    private FaseService faseService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Fase>> buscarTodas() {
        List<Fase> fases = faseService.buscarTodas();
        return ResponseEntity.ok(fases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fase> buscarPorId(@PathVariable Long id) {
        Optional<Fase> fase = faseService.buscarPorId(id);
        return fase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Fase>> buscarPorCursoId(@PathVariable Long cursoId) {
        if (!cursoService.buscarPorId(cursoId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Fase> fases = faseService.buscarPorCursoId(cursoId);
        return ResponseEntity.ok(fases);
    }

    @PostMapping
    public ResponseEntity<Fase> criar(@RequestBody Fase fase) {
        if (fase.getCurso() == null || fase.getCurso().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!cursoService.buscarPorId(fase.getCurso().getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Fase faseCriada = faseService.salvar(fase);
        return ResponseEntity.status(HttpStatus.CREATED).body(faseCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fase> atualizar(@PathVariable Long id, @RequestBody Fase fase) {
        if (!faseService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        fase.setId(id);
        Fase faseAtualizada = faseService.salvar(fase);
        return ResponseEntity.ok(faseAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!faseService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        faseService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

