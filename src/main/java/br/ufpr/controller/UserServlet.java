package br.ufpr.controller;

import java.io.IOException;
import java.util.Arrays;

import br.ufpr.dao.CursoDAO;
import br.ufpr.dao.PessoaDAO;
import br.ufpr.dao.TipoPessoaDAO;
import br.ufpr.dao.UnidadeCurricularDAO;
import br.ufpr.entity.curso.Curso;
import br.ufpr.entity.curso.UnidadeCurricular;
import br.ufpr.entity.pessoa.Pessoa;
import br.ufpr.entity.pessoa.TipoPessoa;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (var em = EMF.createEntityManager()) {

            PessoaDAO pessoaDAO = new PessoaDAO(em);
            TipoPessoaDAO tipoPessoaDAO = new TipoPessoaDAO(em);
            CursoDAO cursoDAO = new CursoDAO(em);
            UnidadeCurricularDAO unidadeDAO = new UnidadeCurricularDAO(em);

            String acao = req.getParameter("acao");
            String idStr = req.getParameter("id");

            if ("editar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                Pessoa user = pessoaDAO.findById(id);
                req.setAttribute("userEditar", user);

            } else if ("deletar".equals(acao) && idStr != null) {

                long id = Long.parseLong(idStr);
                Pessoa user = pessoaDAO.findById(id);

                if (user != null) {
                    pessoaDAO.delete(user);
                }

                resp.sendRedirect(req.getContextPath() + "/users");
                return;
            }

            req.setAttribute("users", pessoaDAO.findAll());
            req.setAttribute("tipos", tipoPessoaDAO.listarTodos());
            req.setAttribute("cursos", cursoDAO.listarTodos());
            req.setAttribute("atividades", unidadeDAO.listarTodos());

            req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try (var em = EMF.createEntityManager()) {

            var pessoaDAO = new PessoaDAO(em);
            var tipoPessoaDAO = new TipoPessoaDAO(em);
            var cursoDAO = new CursoDAO(em);
            var unidadeDAO = new UnidadeCurricularDAO(em);

            String idStr = req.getParameter("id");
            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            String cpf = req.getParameter("cpf");
            int periodo = Integer.parseInt(req.getParameter("periodo"));
            String senha = req.getParameter("senha");

            long tipoId = Long.parseLong(req.getParameter("tipoPessoa"));
            TipoPessoa tipo = tipoPessoaDAO.buscarPorId(tipoId);

            // Lê listas de IDs do input hidden
            String[] cursoIds = req.getParameter("cursoIds").split(",");
            String[] atividadeIds = req.getParameter("atividadeIds").split(",");

            Pessoa pessoa;
            if (idStr != null && !idStr.isEmpty()) {
                long id = Long.parseLong(idStr);
                pessoa = pessoaDAO.findById(id);

                pessoa.setNome(nome);
                pessoa.setEmail(email);
                pessoa.setCpf(cpf);
                pessoa.setPeriodo(periodo);
                pessoa.setSenha(senha);
                pessoa.setTipo(tipo);

            } else {
                pessoa = new Pessoa();
                pessoa.setNome(nome);
                pessoa.setEmail(email);
                pessoa.setCpf(cpf);
                pessoa.setPeriodo(periodo);
                pessoa.setSenha(senha);
                pessoa.setTipo(tipo);
            }

            // Atualiza listas de cursos e atividades
            pessoa.setCurso(Arrays.stream(cursoIds)
                    .filter(s -> !s.isEmpty())
                    .map(s -> cursoDAO.buscarPorId(Long.parseLong(s)))
                    .toList());

            pessoa.setAtividades(Arrays.stream(atividadeIds)
                    .filter(s -> !s.isEmpty())
                    .map(s -> unidadeDAO.buscarPorId(Long.parseLong(s)))
                    .toList());

            if (idStr != null && !idStr.isEmpty()) {
                pessoaDAO.update(pessoa);
            } else {
                pessoaDAO.save(pessoa);
            }

            resp.sendRedirect(req.getContextPath() + "/users");
        }
    }
}