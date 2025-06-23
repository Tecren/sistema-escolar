import React, { useState, useEffect } from 'react';
import api from '../services/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const FasesPage = () => {
  const [fases, setFases] = useState([]);
  const [cursos, setCursos] = useState([]);
  const [faseAtual, setFaseAtual] = useState({
    nomeFase: '',
    qtdDisciplinas: 0,
    qtdProfessores: 0,
    curso: { id: '' }
  });
  const [editando, setEditando] = useState(false);
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });
  const [carregando, setCarregando] = useState(true);

  useEffect(() => {
    carregarCursos();
    carregarFases();
  }, []);

  const carregarCursos = async () => {
    try {
      const response = await api.get('/cursos');
      setCursos(response.data);
    } catch (error) {
      console.error('Erro ao carregar cursos:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de cursos',
        tipo: 'danger'
      });
    }
  };

  const carregarFases = async () => {
    setCarregando(true);
    try {
      const response = await api.get('/fases');
      setFases(response.data);
    } catch (error) {
      console.error('Erro ao carregar fases:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de fases',
        tipo: 'danger'
      });
    } finally {
      setCarregando(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'cursoId') {
      setFaseAtual({
        ...faseAtual,
        curso: { id: value }
      });
    } else {
      setFaseAtual({ ...faseAtual, [name]: value });
    }
  };

  const limparFormulario = () => {
    setFaseAtual({
      nomeFase: '',
      qtdDisciplinas: 0,
      qtdProfessores: 0,
      curso: { id: '' }
    });
    setEditando(false);
  };

  const handleEditar = (fase) => {
    setFaseAtual({
      ...fase,
      curso: { id: fase.curso ? fase.curso.id : '' }
    });
    setEditando(true);
  };

  const handleExcluir = async (id) => {
    if (!window.confirm('Tem certeza que deseja excluir esta fase?')) {
      return;
    }

    try {
      await api.delete(`/fases/${id}`);
      setMensagem({
        texto: 'Fase excluída com sucesso',
        tipo: 'success'
      });
      carregarFases();
    } catch (error) {
      console.error('Erro ao excluir fase:', error);
      setMensagem({
        texto: 'Erro ao excluir a fase',
        tipo: 'danger'
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!faseAtual.nomeFase || !faseAtual.curso.id) {
      setMensagem({
        texto: 'Preencha todos os campos obrigatórios',
        tipo: 'danger'
      });
      return;
    }

    const faseParaEnviar = {
      ...faseAtual,
      qtdDisciplinas: parseInt(faseAtual.qtdDisciplinas),
      qtdProfessores: parseInt(faseAtual.qtdProfessores),
      curso: {
        id: parseInt(faseAtual.curso.id)
      }
    };

    console.log("Enviando para o backend:", JSON.stringify(faseParaEnviar, null, 2));
    console.log("Tipo real:", typeof faseParaEnviar, faseParaEnviar);

    try {
      if (editando) {
        await api.put(`/fases/${faseAtual.id}`, faseParaEnviar, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        setMensagem({
          texto: 'Fase atualizada com sucesso',
          tipo: 'success'
        });
      } else {
        await api.post('/fases', faseParaEnviar, {
          headers: {
            'Content-Type': 'application/json'
          },
          transformRequest: [(data, headers) => {
            headers['Content-Type'] = 'application/json';
            return JSON.stringify(data);
          }]
        });
        setMensagem({
          texto: 'Fase criada com sucesso',
          tipo: 'success'
        });
      }

      limparFormulario();
      carregarFases();
    } catch (error) {
      console.error('Erro ao salvar fase:', error);
      setMensagem({
        texto: 'Erro ao salvar a fase',
        tipo: 'danger'
      });
    }
  };

  const getNomeCurso = (cursoId) => {
    const curso = cursos.find(c => c.id === cursoId);
    return curso ? curso.nomeCurso : 'Curso não encontrado';
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Gerenciamento de Fases</h2>
      
      {mensagem.texto && (
        <div className={`alert alert-${mensagem.tipo}`} role="alert">
          {mensagem.texto}
        </div>
      )}
      
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-header">
              {editando ? 'Editar Fase' : 'Nova Fase'}
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="cursoId" className="form-label">Curso *</label>
                  <select 
                    className="form-select" 
                    id="cursoId" 
                    name="cursoId"
                    value={faseAtual.curso.id}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Selecione um curso</option>
                    {cursos.map((curso) => (
                      <option key={curso.id} value={curso.id}>
                        {curso.nomeCurso}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-3">
                  <label htmlFor="nomeFase" className="form-label">Nome da Fase *</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="nomeFase" 
                    name="nomeFase"
                    value={faseAtual.nomeFase}
                    onChange={handleInputChange}
                    placeholder="Ex: 01-Fase"
                    required
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="qtdDisciplinas" className="form-label">Quantidade de Disciplinas</label>
                  <input 
                    type="number" 
                    className="form-control" 
                    id="qtdDisciplinas" 
                    name="qtdDisciplinas"
                    value={faseAtual.qtdDisciplinas}
                    onChange={handleInputChange}
                    min="0"
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="qtdProfessores" className="form-label">Quantidade de Professores</label>
                  <input 
                    type="number" 
                    className="form-control" 
                    id="qtdProfessores" 
                    name="qtdProfessores"
                    value={faseAtual.qtdProfessores}
                    onChange={handleInputChange}
                    min="0"
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
              Lista de Fases
            </div>
            <div className="card-body">
              {carregando ? (
                <div className="text-center">
                  <div className="spinner-border" role="status">
                    <span className="visually-hidden">Carregando...</span>
                  </div>
                </div>
              ) : fases.length === 0 ? (
                <p className="text-center">Nenhuma fase cadastrada</p>
              ) : (
                <div className="table-responsive">
                  <table className="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Curso</th>
                        <th>Fase</th>
                        <th>Qtd. Disciplinas</th>
                        <th>Qtd. Professores</th>
                        <th>Ações</th>
                      </tr>
                    </thead>
                    <tbody>
                      {fases.map((fase) => (
                        <tr key={fase.id}>
                          <td>{fase.curso ? getNomeCurso(fase.curso.id) : 'N/A'}</td>
                          <td>{fase.nomeFase}</td>
                          <td>{fase.qtdDisciplinas}</td>
                          <td>{fase.qtdProfessores}</td>
                          <td>
                            <button 
                              className="btn btn-sm btn-outline-primary me-2"
                              onClick={() => handleEditar(fase)}
                            >
                              Editar
                            </button>
                            <button 
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleExcluir(fase.id)}
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

export default FasesPage;

