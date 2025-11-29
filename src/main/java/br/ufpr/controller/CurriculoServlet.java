package br.ufpr.controller;

import br.ufpr.dao.CursoDAO;
import br.ufpr.dao.CurriculoDAO;
import br.ufpr.entity.curso.Curriculo;
import br.ufpr.entity.curso.Curso;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/crud/curriculos")
public class CurriculoServlet extends HttpServlet {

    private final CurriculoDAO curriculoDAO = new CurriculoDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        String idStr = req.getParameter("id");

        if ("editar".equals(acao) && idStr != null) {
            long id = Long.parseLong(idStr);
            Curriculo curriculo = curriculoDAO.buscarPorId(id);
            req.setAttribute("curriculoEditar", curriculo);

        } else if ("deletar".equals(acao) && idStr != null) {
            long id = Long.parseLong(idStr);
            Curriculo curriculo = curriculoDAO.buscarPorId(id);

            if (curriculo != null) {
                curriculoDAO.deletar(curriculo);
            }

            resp.sendRedirect(req.getContextPath() + "/crud/curriculos");
            return;
        }

        req.setAttribute("curriculos", curriculoDAO.listarTodos());
        req.setAttribute("cursos", cursoDAO.listarTodos());

        req.getRequestDispatcher("/WEB-INF/crud/curriculo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        int periodo = Integer.parseInt(req.getParameter("periodo"));
        long cursoId = Long.parseLong(req.getParameter("cursoId"));

        Curso curso = cursoDAO.buscarPorId(cursoId);

        if (idStr != null && !idStr.isEmpty()) {
            long id = Long.parseLong(idStr);
            Curriculo curriculo = curriculoDAO.buscarPorId(id);

            if (curriculo != null) {
                curriculo.setPeriodo(periodo);
                curriculo.setCurso(curso);
                curriculoDAO.atualizar(curriculo);
            }

        } else {
            Curriculo curriculo = new Curriculo();
            curriculo.setPeriodo(periodo);
            curriculo.setCurso(curso);
            curriculoDAO.salvar(curriculo);
        }

        resp.sendRedirect(req.getContextPath() + "/crud/curriculos");
    }
}