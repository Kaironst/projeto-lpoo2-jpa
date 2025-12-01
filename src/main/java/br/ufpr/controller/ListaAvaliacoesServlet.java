package br.ufpr.controller;

import br.ufpr.dao.PessoaDAO;
import br.ufpr.entity.avaliacao.Avaliacao;
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
import java.util.LinkedList;

@WebServlet("/lista-avaliacoes")
public class ListaAvaliacoesServlet extends HttpServlet {

  private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try (var em = EMF.createEntityManager()) {

      var pessoaDAO = new PessoaDAO(em);

      // Verifica login
      HttpSession session = req.getSession(false);
      if (session == null || session.getAttribute("usuarioLogado") == null) {
        resp.sendRedirect("login");
        return;
      }

      Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");
      // atualiza usuario
      usuario = pessoaDAO.findById(usuario.getId());

      if (!usuario.getTipo().getPodeResponderForms())
        return;

      LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
      usuario.getAtividades().forEach(e -> e.getAvaliacoes().forEach(a -> avaliacoes.add(a)));

      req.setAttribute("avaliacoes", avaliacoes);
      req.getRequestDispatcher("/WEB-INF/listaAvaliacoes.jsp")
          .forward(req, resp);
    }
  }
}
