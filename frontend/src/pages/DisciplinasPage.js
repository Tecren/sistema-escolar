import React, { useState, useEffect } from 'react';
import api from '../services/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const DisciplinasPage = () => {
  const [disciplinas, setDisciplinas] = useState([]);
  const [fases, setFases] = useState([]);
  const [disciplinaAtual, setDisciplinaAtual] = useState({
    codigoDisciplina: '',
    nomeDisciplina: '',
    diaSemana: '',
    nomeDiaSemana: '',
    qtdProfessores: 0,
    fase: { id: '' }
  });
  const [editando, setEditando] = useState(false);
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });
  const [carregando, setCarregando] = useState(true);
  const [codigosDisciplina, setCodigosDisciplina] = useState({});

  const codigosDiaSemana = {
    '01': 'Domingo',
    '02': 'Segunda-Feira',
    '03': 'Terça-Feira',
    '04': 'Quarta-Feira',
    '05': 'Quinta-Feira',
    '06': 'Sexta-Feira',
    '07': 'Sábado'
  };

  useEffect(() => {
    carregarFases();
    carregarDisciplinas();
    carregarCodigosDisciplina();
  }, []);

  const carregarFases = async () => {
    try {
      const response = await api.get('/fases');
      setFases(response.data);
      console.log('Fases carregadas:', response.data);
    } catch (error) {
      console.error('Erro ao carregar fases:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de fases',
        tipo: 'danger'
      });
    }
  };

  const carregarDisciplinas = async () => {
    setCarregando(true);
    try {
      const response = await api.get('/disciplinas');
      setDisciplinas(response.data);
    } catch (error) {
      console.error('Erro ao carregar disciplinas:', error);
      setMensagem({
        texto: 'Erro ao carregar a lista de disciplinas',
        tipo: 'danger'
      });
    } finally {
      setCarregando(false);
    }
  };

  const carregarCodigosDisciplina = async () => {
    try {
      const response = await api.get('/disciplinas/map');
      setCodigosDisciplina(response.data);
    } catch (error) {
      console.error('Erro ao carregar códigos das disciplinas:', error);
      setMensagem({
        texto: 'Erro ao carregar os códigos das disciplinas',
        tipo: 'danger'
      });
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'faseId') {
      setDisciplinaAtual(prev => ({
        ...prev,
        fase: {
          id: value ? Number(value) : null
        }
      }));
    } else if (name === 'codigoDisciplina') {
      setDisciplinaAtual({
        ...disciplinaAtual,
        codigoDisciplina: value,
        nomeDisciplina: codigosDisciplina[value] || ''
      });
    } else if (name === 'diaSemana') {
      setDisciplinaAtual({
        ...disciplinaAtual,
        diaSemana: value,
        nomeDiaSemana: codigosDiaSemana[value] || ''
      });
    } else if (name === 'qtdProfessores') {
        setDisciplinaAtual({
          ...disciplinaAtual,
          qtdProfessores: Number(value)
        });
    } else {
      setDisciplinaAtual({ ...disciplinaAtual, [name]: value });
    }
  };

  const limparFormulario = () => {
    setDisciplinaAtual({
      codigoDisciplina: '',
      nomeDisciplina: '',
      diaSemana: '',
      nomeDiaSemana: '',
      qtdProfessores: 0,
      fase: { id: '' }
    });
    setEditando(false);
  };

  const handleEditar = (disciplina) => {
    setDisciplinaAtual({
      ...disciplina,
      fase: { id: disciplina.fase ? disciplina.fase.id : '' }
    });
    setEditando(true);
  };

  const handleExcluir = async (id) => {
    if (!window.confirm('Tem certeza que deseja excluir esta disciplina?')) {
      return;
    }

    try {
      await api.delete(`/disciplinas/${id}`);
      setMensagem({
        texto: 'Disciplina excluída com sucesso',
        tipo: 'success'
      });
      carregarDisciplinas();
    } catch (error) {
      console.error('Erro ao excluir disciplina:', error);
      setMensagem({
        texto: 'Erro ao excluir a disciplina',
        tipo: 'danger'
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!disciplinaAtual.codigoDisciplina || !disciplinaAtual.diaSemana || !disciplinaAtual.fase?.id) {
      setMensagem({
        texto: 'Preencha todos os campos obrigatórios',
        tipo: 'danger'
      });
      return;
    }

    const disciplinaParaEnviar = {
      ...disciplinaAtual,
      qtdProfessores: parseInt(disciplinaAtual.qtdProfessores),
      fase: {
        id: parseInt(disciplinaAtual.fase.id)
      }
    };

    try {
      if (editando) {
        await api.put(`/disciplinas/${disciplinaAtual.id}`, disciplinaParaEnviar);
        setMensagem({
          texto: 'Disciplina atualizada com sucesso',
          tipo: 'success'
        });
      } else {
        console.log("Tentando criar disciplina:", disciplinaParaEnviar);
        await api.post('/disciplinas', disciplinaParaEnviar);
        setMensagem({
          texto: 'Disciplina criada com sucesso',
          tipo: 'success'
        });
      }

      limparFormulario();
      carregarDisciplinas();
    } catch (error) {
      console.error('Erro ao salvar disciplina:', error);
      setMensagem({
        texto: 'Erro ao salvar a disciplina',
        tipo: 'danger'
      });
    }
  };

  const getNomeFase = (faseId) => {
    if (!faseId) return 'N/A';
    const fase = fases.find(f => Number(f.id) === Number(faseId));
    return fase ? fase.nomeFase : 'Fase não encontrada';
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Gerenciamento de Disciplinas</h2>
      
      {mensagem.texto && (
        <div className={`alert alert-${mensagem.tipo}`} role="alert">
          {mensagem.texto}
        </div>
      )}
      
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-header">
              {editando ? 'Editar Disciplina' : 'Nova Disciplina'}
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="faseId" className="form-label">Fase *</label>
                  <select 
                    className="form-select" 
                    id="faseId" 
                    name="faseId"
                    value={disciplinaAtual.fase.id}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Selecione uma fase</option>
                    {fases.map((fase) => (
                      <option key={fase.id} value={fase.id}>
                        {fase.nomeFase} - {fase.curso ? fase.curso.nomeCurso : 'Sem curso'}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-3">
                  <label htmlFor="codigoDisciplina" className="form-label">Código da Disciplina *</label>
                  <select 
                    className="form-select" 
                    id="codigoDisciplina" 
                    name="codigoDisciplina"
                    value={disciplinaAtual.codigoDisciplina}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Selecione um código</option>
                    {Object.entries(codigosDisciplina).map(([codigo, nome]) => (
                      <option key={codigo} value={codigo}>
                        {codigo} - {nome}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-3">
                  <label htmlFor="diaSemana" className="form-label">Dia da Semana *</label>
                  <select 
                    className="form-select" 
                    id="diaSemana" 
                    name="diaSemana"
                    value={disciplinaAtual.diaSemana}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Selecione um dia</option>
                    {Object.entries(codigosDiaSemana).map(([codigo, nome]) => (
                      <option key={codigo} value={codigo}>
                        {codigo} - {nome}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-3">
                  <label htmlFor="qtdProfessores" className="form-label">Quantidade de Professores</label>
                  <input 
                    type="number" 
                    className="form-control" 
                    id="qtdProfessores" 
                    name="qtdProfessores"
                    value={disciplinaAtual.qtdProfessores}
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
              Lista de Disciplinas
            </div>
            <div className="card-body">
              {carregando ? (
                <div className="text-center">
                  <div className="spinner-border" role="status">
                    <span className="visually-hidden">Carregando...</span>
                  </div>
                </div>
              ) : disciplinas.length === 0 ? (
                <p className="text-center">Nenhuma disciplina cadastrada</p>
              ) : (
                <div className="table-responsive">
                  <table className="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Fase</th>
                        <th>Código</th>
                        <th>Disciplina</th>
                        <th>Dia</th>
                        <th>Qtd. Professores</th>
                        <th>Ações</th>
                      </tr>
                    </thead>
                    <tbody>
                      {disciplinas.map((disciplina) => (
                        <tr key={disciplina.id}>
                          <td>{typeof disciplina.fase === 'object'? getNomeFase(disciplina.fase.id): getNomeFase(disciplina.fase)}</td>
                          <td>{disciplina.codigoDisciplina}</td>
                          <td>{disciplina.nomeDisciplina}</td>
                          <td>{disciplina.nomeDiaSemana}</td>
                          <td>{disciplina.qtdProfessores}</td>
                          <td>
                            <button 
                              className="btn btn-sm btn-outline-primary me-2"
                              onClick={() => handleEditar(disciplina)}
                            >
                              Editar
                            </button>
                            <button 
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleExcluir(disciplina.id)}
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

export default DisciplinasPage;

