import React, { useState, useEffect } from 'react';
import api from '../services/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const CursosPage = () => {
  const [cursos, setCursos] = useState([]);
  const [cursoAtual, setCursoAtual] = useState({
    nomeCurso: '',
    dataProcessamento: '',
    periodoInicial: '',
    periodoFinal: '',
    sequencia: '',
    versaoLayout: '001',
    hashArquivo: ''
  });
  const [editando, setEditando] = useState(false);
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });
  const [carregando, setCarregando] = useState(true);

  useEffect(() => {
    carregarCursos();
  }, []);

  const carregarCursos = async () => {
    setCarregando(true);
    try {
      const response = await api.get('/cursos');
      setCursos(response.data);
    } catch (error) {
      console.error('Erro ao carregar cursos:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de cursos',
        tipo: 'danger'
      });
    } finally {
      setCarregando(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCursoAtual({ ...cursoAtual, [name]: value });
  };

  const limparFormulario = () => {
    setCursoAtual({
      nomeCurso: '',
      dataProcessamento: '',
      periodoInicial: '',
      periodoFinal: '',
      sequencia: '',
      versaoLayout: '001',
      hashArquivo: ''
    });
    setEditando(false);
  };

  const handleEditar = (curso) => {
    const dataFormatada = curso.dataProcessamento ? curso.dataProcessamento.split('T')[0] : '';
    
    setCursoAtual({
      ...curso,
      dataProcessamento: dataFormatada
    });
    setEditando(true);
  };

  const handleExcluir = async (id) => {
    if (!window.confirm('Tem certeza que deseja excluir este curso?')) {
      return;
    }

    try {
      await api.delete(`/cursos/${id}`);
      setMensagem({
        texto: 'Curso excluído com sucesso',
        tipo: 'success'
      });
      carregarCursos();
    } catch (error) {
      console.error('Erro ao excluir curso:', error);
      setMensagem({
        texto: 'Erro ao excluir o curso',
        tipo: 'danger'
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!cursoAtual.nomeCurso || !cursoAtual.dataProcessamento || !cursoAtual.periodoInicial || !cursoAtual.periodoFinal) {
      setMensagem({
        texto: 'Preencha todos os campos obrigatórios',
        tipo: 'danger'
      });
      return;
    }

    try {
      if (editando) {
        await api.put(`/cursos/${cursoAtual.id}`, cursoAtual);
        setMensagem({
          texto: 'Curso atualizado com sucesso',
          tipo: 'success'
        });
      } else {
        const hashAleatorio = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
        await api.post('/cursos', { ...cursoAtual, hashArquivo: hashAleatorio });
        setMensagem({
          texto: 'Curso criado com sucesso',
          tipo: 'success'
        });
      }
      
      limparFormulario();
      carregarCursos();
    } catch (error) {
      console.error('Erro ao salvar curso:', error);
      setMensagem({
        texto: 'Erro ao salvar o curso',
        tipo: 'danger'
      });
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Gerenciamento de Cursos</h2>
      
      {mensagem.texto && (
        <div className={`alert alert-${mensagem.tipo}`} role="alert">
          {mensagem.texto}
        </div>
      )}
      
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-header">
              {editando ? 'Editar Curso' : 'Novo Curso'}
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="nomeCurso" className="form-label">Nome do Curso *</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="nomeCurso" 
                    name="nomeCurso"
                    value={cursoAtual.nomeCurso}
                    onChange={handleInputChange}
                    required
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="dataProcessamento" className="form-label">Data de Processamento *</label>
                  <input 
                    type="date" 
                    className="form-control" 
                    id="dataProcessamento" 
                    name="dataProcessamento"
                    value={cursoAtual.dataProcessamento}
                    onChange={handleInputChange}
                    required
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="periodoInicial" className="form-label">Período Inicial *</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="periodoInicial" 
                    name="periodoInicial"
                    value={cursoAtual.periodoInicial}
                    onChange={handleInputChange}
                    placeholder="Ex: 01-Fase"
                    required
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="periodoFinal" className="form-label">Período Final *</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="periodoFinal" 
                    name="periodoFinal"
                    value={cursoAtual.periodoFinal}
                    onChange={handleInputChange}
                    placeholder="Ex: 08-Fase"
                    required
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="sequencia" className="form-label">Sequência</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="sequencia" 
                    name="sequencia"
                    value={cursoAtual.sequencia}
                    onChange={handleInputChange}
                    placeholder="Ex: 0000001"
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="versaoLayout" className="form-label">Versão do Layout</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="versaoLayout" 
                    name="versaoLayout"
                    value={cursoAtual.versaoLayout}
                    onChange={handleInputChange}
                    placeholder="Ex: 001"
                  />
                </div>
                
                <div className="d-flex justify-content-between">
                  <button type="submit" className="btn btn-primary">
                    {editando ? 'Atualizar' : 'Salvar'}
                  </button>
                  <button 
                    type="button" 
                    className="btn btn-secondary"
                    onClick={limparFormulario}
                  >
                    Cancelar
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
        
        <div className="col-md-8">
          <div className="card">
            <div className="card-header">
              Lista de Cursos
            </div>
            <div className="card-body">
              {carregando ? (
                <div className="text-center">
                  <div className="spinner-border" role="status">
                    <span className="visually-hidden">Carregando...</span>
                  </div>
                </div>
              ) : cursos.length === 0 ? (
                <p className="text-center">Nenhum curso cadastrado</p>
              ) : (
                <div className="table-responsive">
                  <table className="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Nome</th>
                        <th>Data</th>
                        <th>Período</th>
                        <th>Ações</th>
                      </tr>
                    </thead>
                    <tbody>
                      {cursos.map((curso) => (
                        <tr key={curso.id}>
                          <td>{curso.nomeCurso}</td>
                          <td>{new Date(curso.dataProcessamento).toLocaleDateString('pt-BR')}</td>
                          <td>{curso.periodoInicial} a {curso.periodoFinal}</td>
                          <td>
                            <button 
                              className="btn btn-sm btn-outline-primary me-2"
                              onClick={() => handleEditar(curso)}
                            >
                              Editar
                            </button>
                            <button 
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleExcluir(curso.id)}
                            >
                              Excluir
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CursosPage;

