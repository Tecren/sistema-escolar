package com.unesc.sistemasescolares.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<Curso> buscarTodos() {
        return cursoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorHashArquivo(String hashArquivo) {
        return cursoRepository.findByHashArquivo(hashArquivo);
    }

    @Transactional(readOnly = true)
    public boolean existePorHashArquivo(String hashArquivo) {
        return cursoRepository.existsByHashArquivo(hashArquivo);
    }

    @Transactional
    public Curso salvar(Curso curso) {
        if (curso.getFases() != null) {
            curso.getFases().forEach(fase -> fase.setCurso(curso));
        }
        return cursoRepository.save(curso);
    }

    @Transactional
    public void excluir(Long id) {
        cursoRepository.deleteById(id);
    }
}

