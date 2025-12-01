package br.ufpr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.dao.AvaliacaoDAO;
import br.ufpr.entity.avaliacao.Alternativa;
import br.ufpr.entity.avaliacao.Avaliacao;
import br.ufpr.entity.avaliacao.Questao;
import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/nova-avaliacao")
public class NovaAvaliacaoServlet extends HttpServlet {

  private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Verifica login
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("usuarioLogado") == null) {
      resp.sendRedirect("login");
      return;
    }

    // Apenas encaminha para a JSP — não deve abrir EntityManager!
    req.getRequestDispatcher("/WEB-INF/novaAvaliacao.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

    try (var em = EMF.createEntityManager()) {

      // Verifica login
      HttpSession session = req.getSession(false);
      if (session == null || session.getAttribute("usuarioLogado") == null) {
        resp.sendRedirect("login");
        return;
      }

      Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

      var avaliacaoDAO = new AvaliacaoDAO(em);

      Avaliacao avaliacao = new Avaliacao();
      avaliacao.setAberta(req.getParameter("isAberta") != null);
      avaliacao.setAnon(req.getParameter("isAnon") != null);

      List<Questao> questoes = new ArrayList<>();

      int q = 0;
      while (req.getParameter("questao[" + q + "].enunciado") != null) {

        Questao questao = Questao.builder()
            .enunciado(req.getParameter("questao[" + q + "].enunciado"))
            .valor(Float.parseFloat(req.getParameter("questao[" + q + "].valor")))
            .tipo(Questao.Tipo.valueOf(req.getParameter("questao[" + q + "].tipo")))
            .avaliacao(avaliacao)
            .build();

        List<Alternativa> alternativas = new ArrayList<>();
        int a = 0;

        while (req.getParameter("questao[" + q + "].alternativa[" + a + "].enunciado") != null) {

          Alternativa alt = Alternativa.builder()
              .numero(a + 1)
              .enunciado(req.getParameter("questao[" + q + "].alternativa[" + a + "].enunciado"))
              .correta(req.getParameter("questao[" + q + "].alternativa[" + a + "].correta") != null)
              .questao(questao)
              .build();

          alternativas.add(alt);
          a++;
        }

        questao.setAlternativas(alternativas);
        questoes.add(questao);
        q++;
      }

      avaliacao.setQuestoes(questoes);

      try {
        avaliacaoDAO.salvar(avaliacao);
      } catch (Exception e) {
        throw new ServletException("Erro ao salvar avaliação", e);
      }

      resp.sendRedirect("lista-avaliacoes");
    }
  }
}
