package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.avaliacao.Resposta;
import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/minhas-respostas")
public class MinhasRespostasServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("usuarioLogado") == null) {
      resp.sendRedirect("login");
      return;
    }
    Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

    EntityManager em = Persistence.createEntityManagerFactory("persistence").createEntityManager();

    try {
      TypedQuery<Resposta> query = em.createQuery(
          "SELECT r FROM Resposta r WHERE r.pessoa.id = :usuarioId", Resposta.class);
      query.setParameter("usuarioId", usuario.getId());
      List<Resposta> respostas = query.getResultList();

      req.setAttribute("respostas", respostas);
      req.getRequestDispatcher("/WEB-INF/minhasRespostas.jsp").forward(req, resp);

    } finally {
      em.close();
    }
  }
}
