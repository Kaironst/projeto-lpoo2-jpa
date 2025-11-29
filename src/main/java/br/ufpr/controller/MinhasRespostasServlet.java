package br.ufpr.controller;

import br.ufpr.dao.RespostaDAO;
import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/minhas-respostas")
public class MinhasRespostasServlet extends HttpServlet {

  private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try (var em = EMF.createEntityManager()) {

      var respostaDAO = new RespostaDAO(em);

      HttpSession session = req.getSession(false);
      if (session == null || session.getAttribute("usuarioLogado") == null) {
        resp.sendRedirect(req.getContextPath() + "/login");
        return;
      }

      Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

      req.setAttribute("respostas", respostaDAO.listarPorPessoa(usuario.getId()));

      req.getRequestDispatcher("/WEB-INF/minhasRespostas.jsp").forward(req, resp);
    }
  }

}
