package br.ufpr.controller;

import br.ufpr.dao.CursoDAO;
import br.ufpr.entity.curso.Curso;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/crud/cursos")
public class CursoServlet extends HttpServlet {

    private final CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        String idStr = req.getParameter("id");

        if ("editar".equals(acao) && idStr != null) {
            long id = Long.parseLong(idStr);
            Curso curso = cursoDAO.buscarPorId(id);
            req.setAttribute("cursoEditar", curso);

        } else if ("deletar".equals(acao) && idStr != null) {

            long id = Long.parseLong(idStr);
            Curso curso = cursoDAO.buscarPorId(id);

            if (curso != null) {
                cursoDAO.deletar(curso);
            }

            resp.sendRedirect(req.getContextPath() + "/crud/cursos");
            return;
        }

        req.setAttribute("cursos", cursoDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/crud/curso.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        String nome = req.getParameter("nome");
        int numPeriodos = Integer.parseInt(req.getParameter("numPeriodos"));

        if (idStr != null && !idStr.isEmpty()) {

            long id = Long.parseLong(idStr);
            Curso curso = cursoDAO.buscarPorId(id);

            if (curso != null) {
                curso.setNome(nome);
                curso.setNumPeriodos(numPeriodos);
                cursoDAO.atualizar(curso);
            }

        } else {

            Curso curso = new Curso();
            curso.setNome(nome);
            curso.setNumPeriodos(numPeriodos);
            cursoDAO.salvar(curso);
        }

        resp.sendRedirect(req.getContextPath() + "/crud/cursos");
    }
}