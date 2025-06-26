import React, { useState } from 'react';
import api from '../services/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const ImportacaoPage = () => {
  const [arquivo, setArquivo] = useState(null);
  const [previewConteudo, setPreviewConteudo] = useState('');
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });
  const [carregando, setCarregando] = useState(false);

  const handleArquivoChange = (e) => {
    const arquivoSelecionado = e.target.files[0];
    
    if (!arquivoSelecionado) {
      setArquivo(null);
      setPreviewConteudo('');
      return;
    }

    //ver se o nome é importacao.txt
    if (arquivoSelecionado.name !== 'importacao.txt') {
      setMensagem({
        texto: 'O arquivo deve ter o nome "importacao.txt"',
        tipo: 'danger'
      });
      setArquivo(null);
      setPreviewConteudo('');
      return;
    }

    setArquivo(arquivoSelecionado);
    setMensagem({ texto: '', tipo: '' });

    const reader = new FileReader();
    reader.onload = (e) => {
      setPreviewConteudo(e.target.result);
    };
    reader.readAsText(arquivoSelecionado);
  };

  const formatarLinha = (linha) => {
    if (!linha || linha.trim() === '') return null;

    const tipoRegistro = linha.charAt(0);
    
    switch (tipoRegistro) {
      case '0':
        return (
          <div className="bg-info bg-opacity-25 p-2 mb-2">
            <strong>HEADER:</strong> Curso: {linha.substring(1, 51).trim()}, 
            Data: {linha.substring(51, 59).trim()}, 
            Período Inicial: {linha.substring(59, 66).trim()}, 
            Período Final: {linha.substring(66, 73).trim()}, 
            Sequência: {linha.substring(73, 80).trim()}, 
            Versão: {linha.substring(80, 83).trim()}
          </div>
        );
      case '1':
        return (
          <div className="bg-success bg-opacity-25 p-2 mb-2">
            <strong>FASE:</strong> {linha.substring(1, 8).trim()}, 
            Qtd. Disciplinas: {linha.substring(8, 10).trim()}, 
            Qtd. Professores: {linha.substring(10, 12).trim()}
          </div>
        );
      case '2':
        return (
          <div className="bg-warning bg-opacity-25 p-2 mb-2">
            <strong>DISCIPLINA:</strong> Código: {linha.substring(1, 7).trim()}, 
            Dia da Semana: {linha.substring(7, 9).trim()}, 
            Qtd. Professores: {linha.substring(9, 11).trim()}
          </div>
        );
      case '3':
        return (
          <div className="bg-light p-2 mb-2">
            <strong>PROFESSOR:</strong> Nome: {linha.substring(1, 41).trim()}, 
            Título: {linha.substring(41, 43).trim()}
          </div>
        );
      case '9':
        return (
          <div className="bg-danger bg-opacity-25 p-2 mb-2">
            <strong>TRAILER:</strong> Total de Registros: {linha.substring(1, 12).trim()}
          </div>
        );
      default:
        return (
          <div className="bg-secondary bg-opacity-25 p-2 mb-2">
            {linha}
          </div>
        );
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!arquivo) {
      setMensagem({
        texto: 'Selecione um arquivo para importar',
        tipo: 'danger'
      });
      return;
    }

    setCarregando(true);
    setMensagem({ texto: '', tipo: '' });

    try {
      const formData = new FormData();
      formData.append('arquivo', arquivo);

      const response = await api.post('/api/importacao/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      setMensagem({
        texto: response.data.mensagem,
        tipo: 'success'
      });
    } catch (error) {
      console.error('Erro ao importar arquivo:', error);
      
      let mensagemErro = 'Erro ao importar o arquivo';
      
      if (error.response && error.response.data && error.response.data.mensagem) {
        mensagemErro = error.response.data.mensagem;
      }
      
      setMensagem({
        texto: mensagemErro,
        tipo: 'danger'
      });
    } finally {
      setCarregando(false);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Importação de Arquivo</h2>
      
      {mensagem.texto && (
        <div className={`alert alert-${mensagem.tipo}`} role="alert">
          {mensagem.texto}
        </div>
      )}
      
      <div className="card mb-4">
        <div className="card-header">
          Selecione o arquivo para importação
        </div>
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="arquivo" className="form-label">Arquivo (importacao.txt)</label>
              <input 
                type="file" 
                className="form-control" 
                id="arquivo" 
                onChange={handleArquivoChange}
                accept=".txt"
              />
              <div className="form-text">O arquivo deve ter o nome "importacao.txt" e seguir o formato especificado.</div>
            </div>
            
            <button 
              type="submit" 
              className="btn btn-primary"
              disabled={!arquivo || carregando}
            >
              {carregando ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Importando...
                </>
              ) : 'Importar Arquivo'}
            </button>
          </form>
        </div>
      </div>
      
      {previewConteudo && (
        <div className="card">
          <div className="card-header">
            Preview do Conteúdo
          </div>
          <div className="card-body">
            {previewConteudo.split('\n').map((linha, index) => (
              <React.Fragment key={index}>
                {formatarLinha(linha)}
              </React.Fragment>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default ImportacaoPage;

