package br.ufpr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            var pessoaDAO = new PessoaDAO(em);
            var tipoPessoaDAO = new TipoPessoaDAO(em);
            var cursoDAO = new CursoDAO(em);
            var unidadeDAO = new UnidadeCurricularDAO(em);

            String acao = req.getParameter("acao");
            String idStr = req.getParameter("id");

            if ("editar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                Pessoa user = pessoaDAO.findById(id);

                // Forçar carregamento das coleções
                if (user.getCurso() != null) user.getCurso().size();
                if (user.getAtividades() != null) user.getAtividades().size();

                req.setAttribute("userEditar", user);

            } else if ("deletar".equals(acao) && idStr != null) {
                long id = Long.parseLong(idStr);
                Pessoa user = pessoaDAO.findById(id);
                if (user != null) pessoaDAO.delete(user);
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

            String[] cursoIds = req.getParameterValues("cursoIds");
            String[] atividadeIds = req.getParameterValues("atividadeIds");

            Pessoa pessoa = (idStr != null && !idStr.isEmpty())
                    ? pessoaDAO.findById(Long.parseLong(idStr))
                    : new Pessoa();

            pessoa.setNome(nome);
            pessoa.setEmail(email);
            pessoa.setCpf(cpf);
            pessoa.setPeriodo(periodo);
            pessoa.setSenha(senha);
            pessoa.setTipo(tipo);

            // Busca entidades existentes e relaciona
            List<Curso> cursos = new ArrayList<>();
            if (cursoIds != null) {
                for (String cid : cursoIds) {
                    Curso c = cursoDAO.buscarPorId(Long.parseLong(cid));
                    if (c != null) cursos.add(c);
                }
            }
            pessoa.setCurso(cursos);

            List<UnidadeCurricular> atividades = new ArrayList<>();
            if (atividadeIds != null) {
                for (String aid : atividadeIds) {
                    UnidadeCurricular a = unidadeDAO.buscarPorId(Long.parseLong(aid));
                    if (a != null) atividades.add(a);
                }
            }
            pessoa.setAtividades(atividades);

            // Salva ou atualiza
            pessoaDAO.saveOrUpdate(pessoa);

            resp.sendRedirect(req.getContextPath() + "/users");
        }
    }
}
