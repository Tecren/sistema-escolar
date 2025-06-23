package com.unesc.sistemasescolares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unesc.sistemasescolares.model.Disciplina;
import com.unesc.sistemasescolares.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    List<Professor> findByDisciplina(Disciplina disciplina);
    
    List<Professor> findByDisciplinaId(Long disciplinaId);

    Professor findByNomeProfessorAndDisciplina(String nomeProfessor, Disciplina disciplina);
}

