package com.unesc.sistemasescolares.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unesc.sistemasescolares.model.Disciplina;
import com.unesc.sistemasescolares.model.Fase;
import com.unesc.sistemasescolares.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Transactional(readOnly = true)
    public List<Disciplina> buscarTodas() {
        return disciplinaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Disciplina> buscarPorFase(Fase fase) {
        return disciplinaRepository.findByFase(fase);
    }

    @Transactional(readOnly = true)
    public List<Disciplina> buscarPorFaseId(Long faseId) {
        return disciplinaRepository.findByFaseId(faseId);
    }

    @Transactional(readOnly = true)
    public Disciplina buscarPorCodigoEFase(String codigoDisciplina, Fase fase) {
        return disciplinaRepository.findByCodigoDisciplinaAndFase(codigoDisciplina, fase);
    }

    @Transactional
    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public void excluir(Long id) {
        disciplinaRepository.deleteById(id);
    }
}

