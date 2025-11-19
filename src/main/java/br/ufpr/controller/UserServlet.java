package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Persistência
        EntityManager em = Persistence
                .createEntityManagerFactory("pesistence")
                .createEntityManager();

        List<Pessoa> users = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();

        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/mainpage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(req.getParameter("name"));
        pessoa.setEmail(req.getParameter("email"));

        // Persistência
        EntityManager em = Persistence
                .createEntityManagerFactory("persistence")
                .createEntityManager();
        
        var tx = em.getTransaction();
        tx.begin();
        em.persist(pessoa);
        tx.commit();


        resp.sendRedirect("users");
    }
}