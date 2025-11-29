package br.ufpr.controller;

import br.ufpr.dao.AvaliacaoDAO;
import br.ufpr.dao.RespostaDAO;
import br.ufpr.dao.AlternativaDAO;

import br.ufpr.entity.avaliacao.*;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/resolver-avaliacao")
public class ResolverAvaliacaoServlet extends HttpServlet {

  private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try (var em = EMF.createEntityManager()) {

      var avaliacaoDAO = new AvaliacaoDAO(em);
      var respostaDAO = new RespostaDAO(em);

      String idParam = req.getParameter("id");
      if (idParam == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da avaliação não informado");
        return;
      }

      long id = Long.parseLong(idParam);

      Avaliacao avaliacao = avaliacaoDAO.buscarPorId(id);
      if (avaliacao == null) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avaliação não encontrada");
        return;
      }

      // Verifica login
      HttpSession session = req.getSession(false);
      if (session == null || session.getAttribute("usuarioLogado") == null) {
        resp.sendRedirect("login");
        return;
      }

      Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

      Resposta respostaExistente = respostaDAO.buscarRespostaPorAvaliacaoEUsuario(avaliacao.getId(), usuario.getId());

      if (respostaExistente != null) {
        resp.sendRedirect("minhas-respostas");
        return;
      }

      req.setAttribute("avaliacao", avaliacao);
      req.getRequestDispatcher("/WEB-INF/resolverAvaliacao.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try (var em = EMF.createEntityManager()) {

      var respostaDAO = new RespostaDAO(em);
      var avaliacaoDAO = new AvaliacaoDAO(em);
      var alternativaDAO = new AlternativaDAO(em);

      HttpSession session = req.getSession(false);
      if (session == null || session.getAttribute("usuarioLogado") == null) {
        resp.sendRedirect("login");
        return;
      }
      Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

      long avaliacaoId = Long.parseLong(req.getParameter("avaliacaoId"));
      Avaliacao avaliacao = avaliacaoDAO.buscarPorId(avaliacaoId);

      if (avaliacao == null) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avaliação não encontrada");
        return;
      }

      Resposta resposta = Resposta.builder()
          .avaliacao(avaliacao)
          .pessoa(usuario)
          .build();

      List<RespostaQuestao> respostaQuestoes = new ArrayList<>();

      for (Questao questao : avaliacao.getQuestoes()) {

        RespostaQuestao rq = new RespostaQuestao();
        rq.setQuestao(questao);
        rq.setResposta(resposta);

        String paramName = "resposta[" + questao.getId() + "]";
        String[] valores = req.getParameterValues(paramName);

        if (questao.getTipo() == Questao.Tipo.DISCURSIVA) {
          if (valores != null && valores.length > 0) {
            rq.setRespDiscursiva(valores[0]);
            rq.setCorrecaoDiscursiva(null);
          }

        } else if (questao.getTipo() == Questao.Tipo.OBJETIVA_UNICA) {
          if (valores != null && valores.length > 0) {
            long altId = Long.parseLong(valores[0]);
            Alternativa alt = alternativaDAO.buscarPorId(altId);
            rq.setRespObjetiva(List.of(alt));
          }

        } else if (questao.getTipo() == Questao.Tipo.OBJETIVA_MULTIPLA) {
          if (valores != null && valores.length > 0) {
            List<Alternativa> selecionadas = new ArrayList<>();
            for (String altStr : valores) {
              long altId = Long.parseLong(altStr);
              Alternativa alt = alternativaDAO.buscarPorId(altId);
              if (alt != null)
                selecionadas.add(alt);
            }
            rq.setRespObjetiva(selecionadas);
          }
        }

        respostaQuestoes.add(rq);
      }

      resposta.setRespostaQuestoes(respostaQuestoes);

      respostaDAO.salvar(resposta);

      resp.sendRedirect("lista-avaliacoes");
    }
  }
}
