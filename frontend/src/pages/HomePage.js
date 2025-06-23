import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const HomePage = () => {
  return (
    <div className="container mt-4">
      <div className="jumbotron bg-light p-5 rounded">
        <h1 className="display-4">Sistema Escolar</h1>
        <p className="lead">
          Sistema para gerenciamento de cursos, fases, disciplinas e professores para faculdades.
        </p>
        <hr className="my-4" />
        <p>
          Utilize o menu acima para navegar entre as funcionalidades do sistema ou escolha uma das opções abaixo:
        </p>
        <div className="row mt-4">
          <div className="col-md-6 mb-3">
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">Importação de Arquivo</h5>
                <p className="card-text">
                  Importe dados de cursos, fases, disciplinas e professores a partir de um arquivo de texto.
                </p>
                <Link to="/importacao" className="btn btn-primary">
                  Ir para Importação
                </Link>
              </div>
            </div>
          </div>
          <div className="col-md-6 mb-3">
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">Gerenciamento de Cursos</h5>
                <p className="card-text">
                  Cadastre, edite e exclua cursos no sistema.
                </p>
                <Link to="/cursos" className="btn btn-primary">
                  Gerenciar Cursos
                </Link>
              </div>
            </div>
          </div>
          <div className="col-md-6 mb-3">
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">Gerenciamento de Fases</h5>
                <p className="card-text">
                  Cadastre, edite e exclua fases dos cursos.
                </p>
                <Link to="/fases" className="btn btn-primary">
                  Gerenciar Fases
                </Link>
              </div>
            </div>
          </div>
          <div className="col-md-6 mb-3">
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">Gerenciamento de Disciplinas</h5>
                <p className="card-text">
                  Cadastre, edite e exclua disciplinas das fases.
                </p>
                <Link to="/disciplinas" className="btn btn-primary">
                  Gerenciar Disciplinas
                </Link>
              </div>
            </div>
          </div>
          <div className="col-md-6 mb-3">
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">Gerenciamento de Professores</h5>
                <p className="card-text">
                  Cadastre, edite e exclua professores das disciplinas.
                </p>
                <Link to="/professores" className="btn btn-primary">
                  Gerenciar Professores
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;

