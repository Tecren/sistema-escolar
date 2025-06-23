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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "tb_disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fase", nullable = false)
    @JsonIgnoreProperties({ "disciplinas", "curso", "hibernateLazyInitializer", "handler"})
    //@JsonIgnoreProperties({ "disciplinas", "curso" })
    private Fase fase;

    @Column(name = "codigo_disciplina", nullable = false, length = 6)
    private String codigoDisciplina;

    @Column(name = "nome_disciplina", nullable = false, length = 255)
    private String nomeDisciplina;

    @Column(name = "dia_semana", nullable = false, length = 2)
    private String diaSemana;

    @Column(name = "nome_dia_semana", nullable = false, length = 20)
    private String nomeDiaSemana;

    @Column(name = "qtd_professores", nullable = false)
    private Integer qtdProfessores;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Professor> professores = new ArrayList<>();

    public Disciplina() {
    }

    public Disciplina(String codigoDisciplina, String nomeDisciplina, String diaSemana, String nomeDiaSemana,
            Integer qtdProfessores) {
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.diaSemana = diaSemana;
        this.nomeDiaSemana = nomeDiaSemana;
        this.qtdProfessores = qtdProfessores;
    }

    public void adicionarProfessor(Professor professor) {
        professores.add(professor);
        professor.setDisciplina(this);
    }

    public void removerProfessor(Professor professor) {
        professores.remove(professor);
        professor.setDisciplina(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getNomeDiaSemana() {
        return nomeDiaSemana;
    }

    public void setNomeDiaSemana(String nomeDiaSemana) {
        this.nomeDiaSemana = nomeDiaSemana;
    }

    public Integer getQtdProfessores() {
        return qtdProfessores;
    }

    public void setQtdProfessores(Integer qtdProfessores) {
        this.qtdProfessores = qtdProfessores;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    @Override
    public String toString() {
        return "Disciplina [id=" + id + ", codigoDisciplina=" + codigoDisciplina + ", nomeDisciplina=" + nomeDisciplina
                + ", diaSemana=" + diaSemana + ", nomeDiaSemana=" + nomeDiaSemana + ", qtdProfessores=" + qtdProfessores
                + "]";
    }
}

