--Tabela cursos
CREATE TABLE IF NOT EXISTS tb_cursos (
    id_curso BIGSERIAL PRIMARY KEY,
    nome_curso VARCHAR(255) NOT NULL,
    data_processamento DATE NOT NULL,
    periodo_inicial VARCHAR(7) NOT NULL,
    periodo_final VARCHAR(7) NOT NULL,
    sequencia VARCHAR(7) NOT NULL,
    versao_layout VARCHAR(3) NOT NULL,
    hash_arquivo VARCHAR(64) NOT NULL UNIQUE
);

--Tabela fases
CREATE TABLE IF NOT EXISTS tb_fases (
    id_fase BIGSERIAL PRIMARY KEY,
    id_curso BIGINT NOT NULL,
    nome_fase VARCHAR(7) NOT NULL,
    qtd_disciplinas INTEGER NOT NULL,
    qtd_professores INTEGER NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES tb_cursos(id_curso) ON DELETE CASCADE
);

--Tabela disciplinas
CREATE TABLE IF NOT EXISTS tb_disciplinas (
    id_disciplina BIGSERIAL PRIMARY KEY,
    id_fase BIGINT NOT NULL,
    codigo_disciplina VARCHAR(6) NOT NULL,
    nome_disciplina VARCHAR(255) NOT NULL,
    dia_semana VARCHAR(2) NOT NULL,
    nome_dia_semana VARCHAR(20) NOT NULL,
    qtd_professores INTEGER NOT NULL,
    FOREIGN KEY (id_fase) REFERENCES tb_fases(id_fase) ON DELETE CASCADE
);

--Tabela professores
CREATE TABLE IF NOT EXISTS tb_professores (
    id_professor BIGSERIAL PRIMARY KEY,
    id_disciplina BIGINT NOT NULL,
    nome_professor VARCHAR(255) NOT NULL,
    titulo_docente VARCHAR(2) NOT NULL,
    nome_titulo_docente VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_disciplina) REFERENCES tb_disciplinas(id_disciplina) ON DELETE CASCADE
);

