## Sistema Escolar - Gerenciamento de Cursos, Fases, Disciplinas e Professores

Trabalho de faculdade da matéria de Programação Orientada a Objeto, do professor Matheus Leandro Ferreira (UNESC)

Aluno Guilherme Conti Machado

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

## Estrutura do Projeto

O projeto está dividido em duas partes principais:

- **Backend**: Desenvolvido em Java com Spring Boot (com visualização em swagger), fornece uma API RESTful para operações CRUD e processamento de arquivos.
- **Frontend**: Desenvolvido em React, fornece a interface de usuário para interação com o sistema.

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

## Ferramentas

- Java
- Node.js
- Docker e Docker Compose
- PostgreSQL (via Docker)

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

## Configuração e Execução

- **Compilação do projeto (backend)**: Na pasta backend rode> **mvn clean package -DskipTests**
- **Rodar o docker**: Na raiz do programa rode> **docker-compose build** e depois **docker-compose up -d** ou **docker-compose up --build**
- **Rodar o frontend**: Na pasta frontend rode> **npm start**

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

## Importação de Arquivo

O sistema permite a importação de um arquivo de texto chamado `importacao.txt` com o seguinte formato:

- **Registro 0**: Header do arquivo (curso, data, período, versão)
- **Registro 1**: Resumo das fases
- **Registro 2**: Resumo das disciplinas
- **Registro 3**: Resumo dos professores
- **Registro 9**: Trailer do arquivo

Exemplo de arquivo:
```
0CIENCIA DA COMPUTACAO                             0706202501-Fase08-Fase0000001001
103-Fase0606
22770320301
3Allan Farias Favaro                     02
22770420401
3Diorgines Mattos Machado                02
22770520201
3Sergio Coral                            01
22770620501
3Matheus Leandro Ferreira                02
22730040601
3Adriane Brogni Uggioni                  02
22730910601
3Andrigo Rodrigues                       01
900000000012
```

=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

## Funcionalidades

- **Importação de Arquivo**: Upload e processamento do arquivo `importacao.txt`.
- **CRUD de Cursos**: Gerenciamento de cursos.
- **CRUD de Fases**: Gerenciamento de fases de cursos.
- **CRUD de Disciplinas**: Gerenciamento de disciplinas de fases.
- **CRUD de Professores**: Gerenciamento de professores de disciplinas.

