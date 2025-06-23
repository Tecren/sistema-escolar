import React, { useState, useEffect } from 'react';
import api from '../services/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const ProfessoresPage = () => {
  const [professores, setProfessores] = useState([]);
  const [disciplinas, setDisciplinas] = useState([]);
  const [professorAtual, setProfessorAtual] = useState({
    nomeProfessor: '',
    tituloDocente: '',
    nomeTituloDocente: '',
    disciplina: { id: '' }
  });
  const [editando, setEditando] = useState(false);
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });
  const [carregando, setCarregando] = useState(true);

  const codigosTituloDocente = {
    '01': 'Pós-Graduação',
    '02': 'Mestrado',
    '03': 'Doutorado',
    '04': 'Pós-Doutorado'
  };

  useEffect(() => {
    carregarDisciplinas();
    carregarProfessores();
  }, []);

  const carregarDisciplinas = async () => {
    try {
      const response = await api.get('/disciplinas');
      setDisciplinas(response.data);
    } catch (error) {
      console.error('Erro ao carregar disciplinas:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de disciplinas',
        tipo: 'danger'
      });
    }
  };

  const carregarProfessores = async () => {
    setCarregando(true);
    try {
      const response = await api.get('/professores');
      setProfessores(response.data);
    } catch (error) {
      console.error('Erro ao carregar professores:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de professores',
        tipo: 'danger'
      });
    } finally {
      setCarregando(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'disciplinaId') {
      setProfessorAtual({
        ...professorAtual,
        disciplina: { id: value }
      });
    } else if (name === 'tituloDocente') {
      setProfessorAtual({
        ...professorAtual,
        tituloDocente: value,
        nomeTituloDocente: codigosTituloDocente[value] || ''
      });
    } else {
      setProfessorAtual({ ...professorAtual, [name]: value });
    }
  };

  const limparFormulario = () => {
    setProfessorAtual({
      nomeProfessor: '',
      tituloDocente: '',
      nomeTituloDocente: '',
      disciplina: { id: '' }
    });
    setEditando(false);
  };

  const handleEditar = (professor) => {
    setProfessorAtual({
      ...professor,
      disciplina: { id: professor.disciplina ? professor.disciplina.id : '' }
    });
    setEditando(true);
  };

  const handleExcluir = async (id) => {
    if (!window.confirm('Tem certeza que deseja excluir este professor?')) {
      return;
    }

    try {
      await api.delete(`/professores/${id}`);
      setMensagem({
        texto: 'Professor excluído com sucesso',
        tipo: 'success'
      });
      carregarProfessores();
    } catch (error) {
      console.error('Erro ao excluir professor:', error);
      setMensagem({
        texto: 'Erro ao excluir o professor',
        tipo: 'danger'
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!professorAtual.nomeProfessor || !professorAtual.tituloDocente || !professorAtual.disciplina.id) {
      setMensagem({
        texto: 'Preencha todos os campos obrigatórios',
        tipo: 'danger'
      });
      return;
    }

    try {
      if (editando) {
        await api.put(`/professores/${professorAtual.id}`, professorAtual);
        setMensagem({
          texto: 'Professor atualizado com sucesso',
          tipo: 'success'
        });
      } else {
        await api.post('/professores', professorAtual);
        setMensagem({
          texto: 'Professor criado com sucesso',
          tipo: 'success'
        });
      }
      
      limparFormulario();
      carregarProfessores();
    } catch (error) {
      console.error('Erro ao salvar professor:', error);
      setMensagem({
        texto: 'Erro ao salvar o professor',
        tipo: 'danger'
      });
    }
  };

  const getNomeDisciplina = (disciplinaId) => {
    const disciplina = disciplinas.find(d => d.id === disciplinaId);
    return disciplina ? disciplina.nomeDisciplina : 'Disciplina não encontrada';
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Gerenciamento de Professores</h2>
      
      {mensagem.texto && (
        <div className={`alert alert-${mensagem.tipo}`} role="alert">
          {mensagem.texto}
        </div>
      )}
      
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-header">
              {editando ? 'Editar Professor' : 'Novo Professor'}
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="disciplinaId" className="form-label">Disciplina *</label>
                  <select 
                    className="form-select" 
                    id="disciplinaId" 
                    name="disciplinaId"
                    value={professorAtual.disciplina.id}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Selecione uma disciplina</option>
                    {disciplinas.map((disciplina) => (
                      <option key={disciplina.id} value={disciplina.id}>
                        {disciplina.nomeDisciplina} - {disciplina.fase ? disciplina.fase.nomeFase : 'Sem fase'}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-3">
                  <label htmlFor="nomeProfessor" className="form-label">Nome do Professor *</label>
                  <input 
                    type="text" 
                    className="form-control" 
                    id="nomeProfessor" 
                    name="nomeProfessor"
                    value={professorAtual.nomeProfessor}
                    onChange={handleInputChange}
                    required
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="tituloDocente" className="form-label">Título Docente *</label>
                  <select 
                    className="form-select" 
                    id="tituloDocente" 
                    name="tituloDocente"
                    value={professorAtual.tituloDocente}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Selecione um título</option>
                    {Object.entries(codigosTituloDocente).map(([codigo, nome]) => (
                      <option key={codigo} value={codigo}>
                        {codigo} - {nome}
                      </option>
                    ))}
                  </select>
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
              Lista de Professores
            </div>
            <div className="card-body">
              {carregando ? (
                <div className="text-center">
                  <div className="spinner-border" role="status">
                    <span className="visually-hidden">Carregando...</span>
                  </div>
                </div>
              ) : professores.length === 0 ? (
                <p className="text-center">Nenhum professor cadastrado</p>
              ) : (
                <div className="table-responsive">
                  <table className="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Nome</th>
                        <th>Título</th>
                        <th>Disciplina</th>
                        <th>Ações</th>
                      </tr>
                    </thead>
                    <tbody>
                      {professores.map((professor) => (
                        <tr key={professor.id}>
                          <td>{professor.nomeProfessor}</td>
                          <td>{professor.nomeTituloDocente}</td>
                          <td>{professor.disciplina ? getNomeDisciplina(professor.disciplina.id) : 'N/A'}</td>
                          <td>
                            <button 
                              className="btn btn-sm btn-outline-primary me-2"
                              onClick={() => handleEditar(professor)}
                            >
                              Editar
                            </button>
                            <button 
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleExcluir(professor.id)}
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

export default ProfessoresPage;

