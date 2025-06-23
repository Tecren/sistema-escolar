import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const Footer = () => {
  return (
    <footer className="bg-dark text-white text-center py-3 mt-5">
      <div className="container">
        <p className="mb-0">Sistema Escolar &copy; {new Date().getFullYear()} - Trabalho Escolar</p>
      </div>
    </footer>
  );
};

export default Footer;

