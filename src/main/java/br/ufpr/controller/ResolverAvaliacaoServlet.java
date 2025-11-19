package br.ufpr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.entity.avaliacao.Alternativa;
import br.ufpr.entity.avaliacao.Avaliacao;
import br.ufpr.entity.avaliacao.Questao;
import br.ufpr.entity.avaliacao.Resposta;
import br.ufpr.entity.avaliacao.RespostaQuestao;
import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/resolver-avaliacao")
public class ResolverAvaliacaoServlet extends HttpServlet {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        long id = Long.parseLong(idParam);

<<<<<<< HEAD
        EntityManager em = EMF.createEntityManager();
        try {
            Avaliacao avaliacao = em.find(Avaliacao.class, id);
            req.setAttribute("avaliacao", avaliacao);
            req.getRequestDispatcher("/WEB-INF/resolverAvaliacao.jsp").forward(req, resp);
        } finally {
            em.close(); // garante fechamento da conexão
        }
    }
}
=======
    EntityManager em = Persistence.createEntityManagerFactory("persistence").createEntityManager();
    Avaliacao avaliacao = em.find(Avaliacao.class, id);
    if (avaliacao == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avaliação não encontrada");
      return;
    }

    // login
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("usuarioLogado") == null) {
      resp.sendRedirect("login");
      return;
    }
    Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

    // verifica se já respondeu uma vez
    TypedQuery<Resposta> query = em.createQuery(
        "select r from Resposta r where r.avaliacao.id = :avaliacaoId and r.pessoa.id = :usuarioId", Resposta.class);
    query.setParameter("avaliacaoId", avaliacao.getId());
    query.setParameter("usuarioId", usuario.getId());

    Resposta respostaExistente = query.getResultStream().findFirst().orElse(null);
    if (respostaExistente != null) {
      req.setAttribute("msg", "avaliação já respondida");
      resp.sendRedirect("minhas-respostas");
      em.close();
      return;
    }

    req.setAttribute("avaliacao", avaliacao);
    req.getRequestDispatcher("/WEB-INF/resolverAvaliacao.jsp").forward(req, resp);
    em.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    EntityManager em = Persistence.createEntityManagerFactory("persistence").createEntityManager();

    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("usuarioLogado") == null) {
      resp.sendRedirect("login");
      return;
    }

    Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

    long avaliacaoId = Long.parseLong(req.getParameter("avaliacaoId"));
    Avaliacao avaliacao = em.find(Avaliacao.class, avaliacaoId);
    if (avaliacao == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avaliação não encontrada");
      return;
    }

    // cria a resposta
    Resposta resposta = Resposta.builder().avaliacao(avaliacao).pessoa(usuario).build();

    List<RespostaQuestao> respostaQuestoes = new ArrayList<>();
    em.getTransaction().begin();

    for (Questao questao : avaliacao.getQuestoes()) {
      RespostaQuestao respostaQuestao = new RespostaQuestao();
      respostaQuestao.setQuestao(questao);
      respostaQuestao.setResposta(resposta);

      String paramName = "resposta[" + questao.getId() + "]";
      String[] respostasParam = req.getParameterValues(paramName);

      if (questao.getTipo() == Questao.Tipo.DISCURSIVA) {
        if (respostasParam != null && respostasParam.length > 0) {
          respostaQuestao.setRespDiscursiva(respostasParam[0]);
          respostaQuestao.setCorrecaoDiscursiva(null);
        }
      }

      else if (questao.getTipo() == Questao.Tipo.OBJETIVA_UNICA) {
        if (respostasParam != null && respostasParam.length > 0) {
          long altId = Long.parseLong(respostasParam[0]);
          Alternativa alt = em.find(Alternativa.class, altId);
          respostaQuestao.setRespObjetiva(List.of(alt));
        }
      }

      else if (questao.getTipo() == Questao.Tipo.OBJETIVA_MULTIPLA) {
        if (respostasParam != null && respostasParam.length > 0) {
          List<Alternativa> alternativasSelecionadas = new ArrayList<>();
          for (String altIdStr : respostasParam) {
            long altId = Long.parseLong(altIdStr);
            Alternativa alt = em.find(Alternativa.class, altId);
            if (alt != null) {
              alternativasSelecionadas.add(alt);
            }
          }
          respostaQuestao.setRespObjetiva(alternativasSelecionadas);
        }
      }

      respostaQuestoes.add(respostaQuestao);

    }

    resposta.setRespostaQuestoes(respostaQuestoes);
    em.persist(resposta);
    em.getTransaction().commit();

    resp.sendRedirect("lista-avaliacoes");

    em.close();
  }

}
>>>>>>> 7c4924864cfb30b44089d779a8c0131191e3d856
