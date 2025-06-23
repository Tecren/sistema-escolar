package com.unesc.sistemasescolares.util;

import java.util.HashMap;
import java.util.Map;

public class MapeamentoDados {


    //Tabela I - disciplina
    private static final Map<String, String> TABELA_DISCIPLINAS = new HashMap<>();
    

    //Tabela II - dia da Semana
    private static final Map<String, String> TABELA_DIAS_SEMANA = new HashMap<>();
    

    //Tabela III - titulo do professor
    private static final Map<String, String> TABELA_TITULOS_DOCENTES = new HashMap<>();
    
    static {
        //Tabela I
        //Fase 1
        TABELA_DISCIPLINAS.put("270234","Funções e Derivadas");
        TABELA_DISCIPLINAS.put("276943","Resolução de Problemas Computacionais");
        TABELA_DISCIPLINAS.put("276953","Fundamentos Matemáticos");
        TABELA_DISCIPLINAS.put("276963","Evolução da Tecnologia");
        TABELA_DISCIPLINAS.put("276973","Sistemas Eletrônicos");

        //Fase 2
        TABELA_DISCIPLINAS.put("276982","Lógica Computacional");
        TABELA_DISCIPLINAS.put("276992","Design de Interação");
        TABELA_DISCIPLINAS.put("277002","Sistemas Digitais");
        TABELA_DISCIPLINAS.put("277012","Laboratório de Programação");
        TABELA_DISCIPLINAS.put("277022","Desenvolvimento de Aplicação I");

        //Fase 3
        TABELA_DISCIPLINAS.put("277032","Gerenciamento de Dados I");
        TABELA_DISCIPLINAS.put("277042","Resolução de Problemas Estruturados I");
        TABELA_DISCIPLINAS.put("277052","Arquitetura de Computadores");
        TABELA_DISCIPLINAS.put("277062","Programação Orientada a Objeto");
        TABELA_DISCIPLINAS.put("273004","Cálculo Integral");
        TABELA_DISCIPLINAS.put("273091","Estatística");
        
        //Tabela II
        TABELA_DIAS_SEMANA.put("01", "Domingo");
        TABELA_DIAS_SEMANA.put("02", "Segunda-Feira");
        TABELA_DIAS_SEMANA.put("03", "Terça-Feira");
        TABELA_DIAS_SEMANA.put("04", "Quarta-Feira");
        TABELA_DIAS_SEMANA.put("05", "Quinta-Feira");
        TABELA_DIAS_SEMANA.put("06", "Sexta-Feira");
        TABELA_DIAS_SEMANA.put("07", "Sábado");
        
        //Tabela III
        TABELA_TITULOS_DOCENTES.put("01", "Pós-Graduação");
        TABELA_TITULOS_DOCENTES.put("02", "Mestrado");
        TABELA_TITULOS_DOCENTES.put("03", "Doutorado");
        TABELA_TITULOS_DOCENTES.put("04", "Pós-Doutorado");
    }

    public static String getDescricaoDisciplina(String codigo) {
        return TABELA_DISCIPLINAS.getOrDefault(codigo, "Desconhecido");
    }
    
    public static String getDescricaoDiaSemana(String codigo) {
        return TABELA_DIAS_SEMANA.getOrDefault(codigo, "Desconhecido");
    }

    public static String getDescricaoTituloDocente(String codigo) {
        return TABELA_TITULOS_DOCENTES.getOrDefault(codigo, "Desconhecido");
    }

    public static Map<String, String> getTabelaDisciplinas() {
        return TABELA_DISCIPLINAS;
    }

}

