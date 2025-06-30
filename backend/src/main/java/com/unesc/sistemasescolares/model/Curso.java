package com.unesc.sistemasescolares.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_cursos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fases"})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long id;

    @Column(name = "nome_curso", nullable = false, length = 255)
    private String nomeCurso;

    @Column(name = "data_processamento", nullable = false)
    private LocalDate dataProcessamento;

    @Column(name = "periodo_inicial", nullable = false, length = 7)
    private String periodoInicial;

    @Column(name = "periodo_final", nullable = false, length = 7)
    private String periodoFinal;
    
    @Column(name = "sequencia", nullable = false, length = 7)
    private String sequencia;

    @Column(name = "versao_layout", nullable = false, length = 3)
    private String versaoLayout;

    @Column(name = "hash_arquivo", nullable = false, unique = true, length = 64)
    private String hashArquivo;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"curso", "disciplinas", "professores"})
    private List<Fase> fases = new ArrayList<>();

    public Curso() {
    }

    public Curso(String nomeCurso, LocalDate dataProcessamento, String periodoInicial, String periodoFinal,
            String sequencia, String versaoLayout, String hashArquivo) {
        this.nomeCurso = nomeCurso;
        this.dataProcessamento = dataProcessamento;
        this.periodoInicial = periodoInicial;
        this.periodoFinal = periodoFinal;
        this.sequencia = sequencia;
        this.versaoLayout = versaoLayout;
        this.hashArquivo = hashArquivo;
    }

    public void adicionarFase(Fase fase) {
        fases.add(fase);
        fase.setCurso(this);
    }

    public void removerFase(Fase fase) {
        fases.remove(fase);
        fase.setCurso(null);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public LocalDate getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDate dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(String periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public String getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(String periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public String getSequencia() {
        return sequencia;
    }

    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }

    public String getVersaoLayout() {
        return versaoLayout;
    }

    public void setVersaoLayout(String versaoLayout) {
        this.versaoLayout = versaoLayout;
    }

    public String getHashArquivo() {
        return hashArquivo;
    }

    public void setHashArquivo(String hashArquivo) {
        this.hashArquivo = hashArquivo;
    }

    public List<Fase> getFases() {
        return fases;
    }

    public void setFases(List<Fase> fases) {
        this.fases = fases;
    }

}

