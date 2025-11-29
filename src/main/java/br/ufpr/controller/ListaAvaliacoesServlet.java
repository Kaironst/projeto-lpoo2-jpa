package br.ufpr.controller;

import br.ufpr.dao.AvaliacaoDAO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/lista-avaliacoes")
public class ListaAvaliacoesServlet extends HttpServlet {

  private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try (var em = EMF.createEntityManager()) {

      var avaliacaoDAO = new AvaliacaoDAO(em);

      req.setAttribute("avaliacoes", avaliacaoDAO.listarTodos());
      req.getRequestDispatcher("/WEB-INF/listaAvaliacoes.jsp")
          .forward(req, resp);
    }
  }
}
