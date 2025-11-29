package br.ufpr.controller;

import br.ufpr.dao.PessoaDAO;
import br.ufpr.entity.pessoa.Pessoa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final PessoaDAO pessoaDAO = new PessoaDAO();

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

        Pessoa usuario = pessoaDAO.buscarPorEmailSenha(email, senha);

        if (usuario != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            resp.sendRedirect("lista-avaliacoes");
        } else {
            req.setAttribute("erro", "login inválido");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }
}