package com.unesc.sistemasescolares.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unesc.sistemasescolares.model.Disciplina;
import com.unesc.sistemasescolares.model.Professor;
import com.unesc.sistemasescolares.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public List<Professor> buscarTodos() {
        return professorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Professor> buscarPorId(Long id) {
        return professorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Professor> buscarPorDisciplina(Disciplina disciplina) {
        return professorRepository.findByDisciplina(disciplina);
    }

    @Transactional(readOnly = true)
    public List<Professor> buscarPorDisciplinaId(Long disciplinaId) {
        return professorRepository.findByDisciplinaId(disciplinaId);
    }

    @Transactional(readOnly = true)
    public Professor buscarPorNomeEDisciplina(String nomeProfessor, Disciplina disciplina) {
        return professorRepository.findByNomeProfessorAndDisciplina(nomeProfessor, disciplina);
    }

    @Transactional
    public Professor salvar(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional
    public void excluir(Long id) {
        professorRepository.deleteById(id);
    }
}

