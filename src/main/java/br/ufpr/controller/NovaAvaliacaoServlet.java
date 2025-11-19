package br.ufpr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.entity.avaliacao.Alternativa;
import br.ufpr.entity.avaliacao.Avaliacao;
import br.ufpr.entity.avaliacao.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/nova-avaliacao")
public class NovaAvaliacaoServlet extends HttpServlet {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = EMF.createEntityManager();
        try {
            em.find(Avaliacao.class, 1); // apenas teste, pode ser removido
            req.getRequestDispatcher("/WEB-INF/novaAvaliacao.jsp").forward(req, resp);
        } finally {
            em.close(); // garante fechamento da conexão
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

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

        // Hibernate Persist com try-finally
        EntityManager em = EMF.createEntityManager();
        try {
            var transaction = em.getTransaction();
            transaction.begin();
            em.persist(avaliacao);
            transaction.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ServletException("Erro ao salvar avaliação", e);
        } finally {
            em.close(); // garante fechamento da conexão
        }

        resp.sendRedirect("lista-avaliacoes");
    }
}