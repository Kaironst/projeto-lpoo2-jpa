package br.ufpr.controller;

import java.io.IOException;
import java.util.List;

import br.ufpr.entity.curso.Curriculo;
import br.ufpr.entity.curso.UnidadeCurricular;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/crud/unidades")
public class UnidadeCurricularServlet extends HttpServlet {

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
                UnidadeCurricular unidade = em.find(UnidadeCurricular.class, id);
                req.setAttribute("unidadeEditar", unidade);

            } else if ("deletar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                UnidadeCurricular unidade = em.find(UnidadeCurricular.class, id);

                if (unidade != null) {
                    var tx = em.getTransaction();
                    tx.begin();
                    em.remove(unidade);
                    tx.commit();
                }

                resp.sendRedirect(req.getContextPath() + "/crud/unidades");
                return; // evita continuar processando após redirect
            }

            // Listagem
            List<UnidadeCurricular> unidades = em.createQuery("SELECT u FROM UnidadeCurricular u", UnidadeCurricular.class)
                                                 .getResultList();
            List<Curriculo> curriculos = em.createQuery("SELECT c FROM Curriculo c", Curriculo.class)
                                          .getResultList();

            req.setAttribute("unidades", unidades);
            req.setAttribute("curriculos", curriculos);

            req.getRequestDispatcher("/WEB-INF/crud/unidadeCurricular.jsp").forward(req, resp);

        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = EMF.createEntityManager();
        try {
            String idStr = req.getParameter("id");
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");
            String tipoStr = req.getParameter("tipo");
            long curriculoId = Long.parseLong(req.getParameter("curriculoId"));

            Curriculo curriculo = em.find(Curriculo.class, curriculoId);
            UnidadeCurricular.Tipo tipo = UnidadeCurricular.Tipo.valueOf(tipoStr);

            var tx = em.getTransaction();
            tx.begin();

            if (idStr != null && !idStr.isEmpty()) {
                // Edição
                long id = Long.parseLong(idStr);
                UnidadeCurricular unidade = em.find(UnidadeCurricular.class, id);
                if (unidade != null) {
                    unidade.setNome(nome);
                    unidade.setDescricao(descricao);
                    unidade.setTipo(tipo);
                    unidade.setCurriculo(curriculo);
                    em.merge(unidade);
                }
            } else {
                // Nova Unidade Curricular
                UnidadeCurricular unidade = new UnidadeCurricular();
                unidade.setNome(nome);
                unidade.setDescricao(descricao);
                unidade.setTipo(tipo);
                unidade.setCurriculo(curriculo);
                em.persist(unidade);
            }

            tx.commit();
            resp.sendRedirect(req.getContextPath() + "/crud/unidades");

        } finally {
            em.close();
        }
    }
}