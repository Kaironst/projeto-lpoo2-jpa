package br.ufpr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.entity.avaliacao.Alternativa;
import br.ufpr.entity.avaliacao.Avaliacao;
import br.ufpr.entity.avaliacao.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/nova-avaliacao")
public class NovaAvaliacaoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/novaAvaliacao.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setAberta(req.getParameter("isAberta") != null);
		avaliacao.setAnon(req.getParameter("isAnon") != null);

		List<Questao> questoes = new ArrayList<>();

		int q = 0;
		while (req.getParameter("questao[" + q + "].enunciado") != null) {

			Questao questao = new Questao();
			questao.setEnunciado(req.getParameter("questao[" + q + "].enunciado"));
			questao.setValor(Float.parseFloat(
					req.getParameter("questao[" + q + "].valor")));
			questao.setTipo(Questao.Tipo.valueOf(
					req.getParameter("questao[" + q + "].tipo")));
			questao.setAvaliacao(avaliacao);

			// alternativas (if any)
			List<Alternativa> alternativas = new ArrayList<>();
			int a = 0;
			while (req.getParameter("questao[" + q + "].alternativa[" + a + "].enunciado") != null) {

				Alternativa alt = new Alternativa();
				alt.setNumero(a + 1);
				alt.setEnunciado(req.getParameter("questao[" + q + "].alternativa[" + a + "].enunciado"));
				alt.setCorreta(req.getParameter("questao[" + q + "].alternativa[" + a + "].correta") != null);
				alt.setQuestao(questao);

				alternativas.add(alt);
				a++;
			}

			questao.setAlternativas(alternativas);
			questoes.add(questao);
			q++;
		}

		avaliacao.setQuestoes(questoes);

		// Hibernate Persist
		EntityManager em = Persistence.createEntityManagerFactory("avaliacao").createEntityManager();
		var transaction = em.getTransaction();
		transaction.begin();
		em.persist(avaliacao);
		transaction.commit();

		resp.sendRedirect("avaliacao-list");
	}

}
