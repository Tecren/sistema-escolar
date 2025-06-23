import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './pages/HomePage';
import ImportacaoPage from './pages/ImportacaoPage';
import CursosPage from './pages/CursosPage';
import FasesPage from './pages/FasesPage';
import DisciplinasPage from './pages/DisciplinasPage';
import ProfessoresPage from './pages/ProfessoresPage';

function App() {
  return (
    <Router>
      <div className="d-flex flex-column min-vh-100">
        <Header />
        <main className="flex-grow-1">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/importacao" element={<ImportacaoPage />} />
            <Route path="/cursos" element={<CursosPage />} />
            <Route path="/fases" element={<FasesPage />} />
            <Route path="/disciplinas" element={<DisciplinasPage />} />
            <Route path="/professores" element={<ProfessoresPage />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;

