package com.unesc.sistemasescolares.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unesc.sistemasescolares.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    Optional<Curso> findByHashArquivo(String hashArquivo);
    
    boolean existsByHashArquivo(String hashArquivo);
}

