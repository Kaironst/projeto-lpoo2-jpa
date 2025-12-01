package br.ufpr.controller;

import br.ufpr.dao.CurriculoDAO;
import br.ufpr.dao.UnidadeCurricularDAO;
import br.ufpr.entity.curso.Curriculo;
import br.ufpr.entity.curso.UnidadeCurricular;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/crud/unidades")
public class UnidadeCurricularServlet extends HttpServlet {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (var em = EMF.createEntityManager()) {

            UnidadeCurricularDAO unidadeDAO = new UnidadeCurricularDAO(em);
            CurriculoDAO curriculoDAO = new CurriculoDAO(em);

            String acao = req.getParameter("acao");
            String idStr = req.getParameter("id");

            if ("editar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                UnidadeCurricular unidade = unidadeDAO.buscarPorId(id);
                req.setAttribute("unidadeEditar", unidade);
            }

            else if ("deletar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                UnidadeCurricular unidade = unidadeDAO.buscarPorId(id);

                if (unidade != null) {
                    unidadeDAO.deletar(unidade);
                }

                resp.sendRedirect(req.getContextPath() + "/crud/unidades");
                return;
            }

            req.setAttribute("unidades", unidadeDAO.listarTodos());
            req.setAttribute("curriculos", curriculoDAO.listarTodos());

            req.getRequestDispatcher("/WEB-INF/crud/unidadeCurricular.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try (var em = EMF.createEntityManager()) {

            UnidadeCurricularDAO unidadeDAO = new UnidadeCurricularDAO(em);
            CurriculoDAO curriculoDAO = new CurriculoDAO(em);

            String idStr = req.getParameter("id");
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");
            String tipoStr = req.getParameter("tipo");
            long curriculoId = Long.parseLong(req.getParameter("curriculoId"));

            Curriculo curriculo = curriculoDAO.buscarPorId(curriculoId);
            UnidadeCurricular.Tipo tipo = UnidadeCurricular.Tipo.valueOf(tipoStr);

            if (idStr != null && !idStr.isEmpty()) {
                long id = Long.parseLong(idStr);
                UnidadeCurricular unidade = unidadeDAO.buscarPorId(id);

                unidade.setNome(nome);
                unidade.setDescricao(descricao);
                unidade.setTipo(tipo);
                unidade.setCurriculo(curriculo);

                unidadeDAO.atualizar(unidade);
            }

            else {
                UnidadeCurricular unidade = new UnidadeCurricular();
                unidade.setNome(nome);
                unidade.setDescricao(descricao);
                unidade.setTipo(tipo);
                unidade.setCurriculo(curriculo);

                unidadeDAO.salvar(unidade);
            }

            resp.sendRedirect(req.getContextPath() + "/crud/unidades");
        }
    }
}