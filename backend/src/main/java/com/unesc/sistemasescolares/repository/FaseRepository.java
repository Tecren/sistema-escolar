package com.unesc.sistemasescolares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unesc.sistemasescolares.model.Curso;
import com.unesc.sistemasescolares.model.Fase;

@Repository
public interface FaseRepository extends JpaRepository<Fase, Long> {
    
    List<Fase> findByCurso(Curso curso);
    
    List<Fase> findByCursoId(Long cursoId);
    
    Fase findByNomeFaseAndCurso(String nomeFase, Curso curso);
}

