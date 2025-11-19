package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.curso.Curso;
import br.ufpr.entity.curso.Curriculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/crud/curriculos")
public class CurriculoServlet extends HttpServlet {

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
                Curriculo curriculo = em.find(Curriculo.class, id);
                req.setAttribute("curriculoEditar", curriculo);

            } else if ("deletar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                Curriculo curriculo = em.find(Curriculo.class, id);

                if (curriculo != null) {
                    var tx = em.getTransaction();
                    tx.begin();
                    em.remove(curriculo); // cascata remove UnidadesCurriculares
                    tx.commit();
                }

                resp.sendRedirect(req.getContextPath() + "/crud/curriculos");
                return; // evita continuar processando após redirect
            }

            // Listagem
            List<Curriculo> curriculos = em.createQuery("SELECT c FROM Curriculo c", Curriculo.class).getResultList();
            List<Curso> cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();

            req.setAttribute("curriculos", curriculos);
            req.setAttribute("cursos", cursos);

            req.getRequestDispatcher("/WEB-INF/crud/curriculo.jsp").forward(req, resp);

        } finally {
            em.close(); // garante fechamento da conexão
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = EMF.createEntityManager();
        try {
            String idStr = req.getParameter("id");
            int periodo = Integer.parseInt(req.getParameter("periodo"));
            long cursoId = Long.parseLong(req.getParameter("cursoId"));

            Curso curso = em.find(Curso.class, cursoId);

            var tx = em.getTransaction();
            tx.begin();

            if (idStr != null && !idStr.isEmpty()) {
                // Edição
                long id = Long.parseLong(idStr);
                Curriculo curriculo = em.find(Curriculo.class, id);
                if (curriculo != null) {
                    curriculo.setPeriodo(periodo);
                    curriculo.setCurso(curso);
                    em.merge(curriculo);
                }
            } else {
                // Novo Currículo
                Curriculo curriculo = new Curriculo();
                curriculo.setPeriodo(periodo);
                curriculo.setCurso(curso);
                em.persist(curriculo);
            }

            tx.commit();
            resp.sendRedirect(req.getContextPath() + "/crud/curriculos");

        } finally {
            em.close(); // garante fechamento da conexão
        }
    }
}