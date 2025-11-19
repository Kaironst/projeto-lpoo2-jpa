package br.ufpr.controller;

import java.io.IOException;

import br.ufpr.entity.avaliacao.Avaliacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/resolver-avaliacao")
public class ResolverAvaliacaoServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String idParam = req.getParameter("id");

    long id = Long.parseLong(idParam);

    EntityManager em = Persistence.createEntityManagerFactory("persistence").createEntityManager();
    Avaliacao avaliacao = em.find(Avaliacao.class, id);

    req.setAttribute("avaliacao", avaliacao);
    req.getRequestDispatcher("/WEB-INF/resolverAvaliacao.jsp").forward(req, resp);

  }

}
