package br.ufpr.controller;

import java.io.IOException;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Pessoa> query = em.createQuery(
                    "SELECT p FROM Pessoa p WHERE p.email = :email AND p.senha = :senha", Pessoa.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            Pessoa usuario = query.getResultStream().findFirst().orElse(null);

            if (usuario != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("usuarioLogado", usuario);
                resp.sendRedirect("lista-avaliacoes");
            } else {
                req.setAttribute("erro", "login inválido");
                req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            }
        } finally {
            em.close(); // garante fechamento da conexão
        }
    }
}