package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.curso.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/crud/cursos")
public class CursoServlet extends HttpServlet {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = EMF.createEntityManager();
        try {
            String acao = req.getParameter("acao");
            String idStr = req.getParameter("id");

            if ("editar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                Curso curso = em.find(Curso.class, id);
                req.setAttribute("cursoEditar", curso);

            } else if ("deletar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                Curso curso = em.find(Curso.class, id);

                if (curso != null) {
                    var tx = em.getTransaction();
                    tx.begin();
                    em.remove(curso); // Cascata remove Curriculos e UnidadesCurriculares automaticamente
                    tx.commit();
                }

                resp.sendRedirect(req.getContextPath() + "/crud/cursos");
                return; // importante retornar após o redirect
            }

            // Listagem de todos os cursos
            List<Curso> cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
            req.setAttribute("cursos", cursos);

            req.getRequestDispatcher("/WEB-INF/crud/curso.jsp").forward(req, resp);

        } finally {
            em.close(); // sempre fechar, mesmo que ocorra exceção
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = EMF.createEntityManager();
        try {
            String idStr = req.getParameter("id");
            String nome = req.getParameter("nome");
            int numPeriodos = Integer.parseInt(req.getParameter("numPeriodos"));

            var tx = em.getTransaction();
            tx.begin();

            if (idStr != null && !idStr.isEmpty()) {
                // Edição
                long id = Long.parseLong(idStr);
                Curso curso = em.find(Curso.class, id);
                if (curso != null) {
                    curso.setNome(nome);
                    curso.setNumPeriodos(numPeriodos);
                    em.merge(curso);
                }
            } else {
                // Novo curso
                Curso curso = new Curso();
                curso.setNome(nome);
                curso.setNumPeriodos(numPeriodos);
                em.persist(curso);
            }

            tx.commit();
            resp.sendRedirect(req.getContextPath() + "/crud/cursos");

        } finally {
            em.close(); // sempre fechar
        }
    }
}