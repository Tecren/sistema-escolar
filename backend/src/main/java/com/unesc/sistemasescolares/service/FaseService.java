package com.unesc.sistemasescolares.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.model.Fase;
import com.unesc.sistemasescolares.repository.FaseRepository;

@Service
public class FaseService {

    @Autowired
    private FaseRepository faseRepository;

    @Transactional(readOnly = true)
    public List<Fase> buscarTodas() {
        return faseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Fase> buscarPorId(Long id) {
        return faseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Fase> buscarPorCurso(Curso curso) {
        return faseRepository.findByCurso(curso);
    }

    @Transactional(readOnly = true)
    public List<Fase> buscarPorCursoId(Long cursoId) {
        return faseRepository.findByCursoId(cursoId);
    }

    @Transactional(readOnly = true)
    public Fase buscarPorNomeFaseECurso(String nomeFase, Curso curso) {
        return faseRepository.findByNomeFaseAndCurso(nomeFase, curso);
    }

    @Transactional
    public Fase salvar(Fase fase) {
        return faseRepository.save(fase);
    }

    @Transactional
    public void excluir(Long id) {
        faseRepository.deleteById(id);
    }
}

