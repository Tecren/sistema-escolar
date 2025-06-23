package com.unesc.sistemasescolares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unesc.sistemasescolares.model.Disciplina;
import com.unesc.sistemasescolares.model.Fase;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    
    List<Disciplina> findByFase(Fase fase);

    List<Disciplina> findByFaseId(Long faseId);
    
    Disciplina findByCodigoDisciplinaAndFase(String codigoDisciplina, Fase fase);
}

