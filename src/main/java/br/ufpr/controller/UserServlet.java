package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.pessoa.Pessoa;
import br.ufpr.entity.pessoa.TipoPessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        EntityManager em = EMF.createEntityManager();
        try {
            List<Pessoa> users = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
            List<TipoPessoa> tipos = em.createQuery("SELECT t FROM TipoPessoa t", TipoPessoa.class).getResultList();

            // Caso não exista nenhum tipo, cria os padrões
            if (tipos.isEmpty()) {
                em.getTransaction().begin();
                em.persist(new TipoPessoa("aluno", false, true));
                em.persist(new TipoPessoa("professor", true, true));
                em.persist(new TipoPessoa("coordenador", true, true));
                em.persist(new TipoPessoa("administrador", true, true));
                em.getTransaction().commit();

                tipos = em.createQuery("SELECT t FROM TipoPessoa t", TipoPessoa.class).getResultList();
            }
            
            req.setAttribute("users", users);
            req.setAttribute("tipos", tipos);
            req.getRequestDispatcher("/WEB-INF/mainpage.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        EntityManager em = EMF.createEntityManager();
        try {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(req.getParameter("nome"));
            pessoa.setEmail(req.getParameter("email"));
            pessoa.setCpf(req.getParameter("cpf"));
            try {
                pessoa.setPeriodo(Integer.valueOf(req.getParameter("periodo")));
            } catch (Exception e) {
                pessoa.setPeriodo(1); // default pra 1
            }
            
            long tipoId = Long.parseLong(req.getParameter("tipoPessoa"));
            TipoPessoa tipo = em.find(TipoPessoa.class, tipoId);
            pessoa.setTipo(tipo);
            pessoa.setSenha(req.getParameter("senha"));

            var tx = em.getTransaction();
            tx.begin();
            em.persist(pessoa);
            tx.commit();

            resp.sendRedirect("users");
        } finally {
            em.close();
        }
    }
}