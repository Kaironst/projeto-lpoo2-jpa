package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.avaliacao.Avaliacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lista-avaliacoes")
public class ListaAvaliacoesServerlet extends HttpServlet {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = EMF.createEntityManager();
        try {
            List<Avaliacao> avaliacoes = em.createQuery("SELECT a FROM Avaliacao a", Avaliacao.class)
                                           .getResultList();
            req.setAttribute("avaliacoes", avaliacoes);
            req.getRequestDispatcher("/WEB-INF/listaAvaliacoes.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }
}