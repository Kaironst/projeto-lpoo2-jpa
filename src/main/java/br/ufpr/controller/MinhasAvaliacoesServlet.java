package br.ufpr.controller;

import br.ufpr.dao.AvaliacaoDAO;
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
import java.util.List;

@WebServlet("/minhas-avaliacoes")
public class MinhasAvaliacoesServlet extends HttpServlet {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (var em = EMF.createEntityManager()) {

            var pessoaDAO = new PessoaDAO(em);

            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("usuarioLogado") == null) {
                resp.sendRedirect("login");
                return;
            }

            Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");
            usuario = pessoaDAO.findById(usuario.getId());

            List<Avaliacao> minhasAvaliacoes = usuario.getAvaliacoesCriadas();

            req.setAttribute("avaliacoes", minhasAvaliacoes);
            req.getRequestDispatcher("/WEB-INF/minhasAvaliacoes.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try (var em = EMF.createEntityManager()) {
            var avaliacaoDAO = new AvaliacaoDAO(em);

            long id = Long.parseLong(req.getParameter("id"));
            Avaliacao av = avaliacaoDAO.buscarPorId(id);

            if (av != null) {
                //toggle de aberta/fechada
                av.setAberta(!av.isAberta());
                avaliacaoDAO.atualizar(av);
            }

            resp.sendRedirect("minhas-avaliacoes");
        }
    }
}