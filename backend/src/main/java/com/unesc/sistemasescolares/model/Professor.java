package com.unesc.sistemasescolares.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tb_professores")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    @Column(name = "nome_professor", nullable = false, length = 255)
    private String nomeProfessor;

    @Column(name = "titulo_docente", nullable = false, length = 2)
    private String tituloDocente;

    @Column(name = "nome_titulo_docente", nullable = false, length = 50)
    private String nomeTituloDocente;

    public Professor() {
    }

    public Professor(String nomeProfessor, String tituloDocente, String nomeTituloDocente) {
        this.nomeProfessor = nomeProfessor;
        this.tituloDocente = tituloDocente;
        this.nomeTituloDocente = nomeTituloDocente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getTituloDocente() {
        return tituloDocente;
    }

    public void setTituloDocente(String tituloDocente) {
        this.tituloDocente = tituloDocente;
    }

    public String getNomeTituloDocente() {
        return nomeTituloDocente;
    }

    public void setNomeTituloDocente(String nomeTituloDocente) {
        this.nomeTituloDocente = nomeTituloDocente;
    }

}

