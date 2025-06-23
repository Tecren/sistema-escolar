package com.unesc.sistemasescolares.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "tb_fases")
public class Fase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fase")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_curso", nullable = false)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fases"})
    private Curso curso;

    @Column(name = "nome_fase", nullable = false, length = 7)
    private String nomeFase;

    @Column(name = "qtd_disciplinas", nullable = false)
    private Integer qtdDisciplinas;

    @Column(name = "qtd_professores", nullable = false)
    private Integer qtdProfessores;

    @OneToMany(mappedBy = "fase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Fase() {
    }

    public Fase(String nomeFase, Integer qtdDisciplinas, Integer qtdProfessores) {
        //this.curso = curso;
        this.nomeFase = nomeFase;
        this.qtdDisciplinas = qtdDisciplinas;
        this.qtdProfessores = qtdProfessores;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
        disciplina.setFase(this);
    }

    public void removerDisciplina(Disciplina disciplina) {
        disciplinas.remove(disciplina);
        disciplina.setFase(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getNomeFase() {
        return nomeFase;
    }

    public void setNomeFase(String nomeFase) {
        this.nomeFase = nomeFase;
    }

    public Integer getQtdDisciplinas() {
        return qtdDisciplinas;
    }

    public void setQtdDisciplinas(Integer qtdDisciplinas) {
        this.qtdDisciplinas = qtdDisciplinas;
    }

    public Integer getQtdProfessores() {
        return qtdProfessores;
    }

    public void setQtdProfessores(Integer qtdProfessores) {
        this.qtdProfessores = qtdProfessores;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Fase [id=" + id + ", nomeFase=" + nomeFase + ", qtdDisciplinas=" + qtdDisciplinas + ", qtdProfessores="
                + qtdProfessores + "]";
    }
}

