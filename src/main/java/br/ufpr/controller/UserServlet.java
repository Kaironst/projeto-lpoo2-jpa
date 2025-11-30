package br.ufpr.controller;

import java.io.IOException;

import br.ufpr.dao.PessoaDAO;
import br.ufpr.dao.TipoPessoaDAO;
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

      var tipoPessoaDAO = new TipoPessoaDAO(em);
      var pessoaDAO = new PessoaDAO(em);

      tipoPessoaDAO.createDefaultTypesIfEmpty();

      req.setAttribute("users", pessoaDAO.findAll());
      req.setAttribute("tipos", tipoPessoaDAO.findAll());

      req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    try (var em = EMF.createEntityManager()) {

      var tipoPessoaDAO = new TipoPessoaDAO(em);
      var pessoaDAO = new PessoaDAO(em);

      Pessoa pessoa = new Pessoa();
      pessoa.setNome(req.getParameter("nome"));
      pessoa.setEmail(req.getParameter("email"));
      pessoa.setCpf(req.getParameter("cpf"));

      try {
        pessoa.setPeriodo(Integer.valueOf(req.getParameter("periodo")));
      } catch (Exception e) {
        pessoa.setPeriodo(1);
      }

      long tipoId = Long.parseLong(req.getParameter("tipoPessoa"));
      TipoPessoa tipo = tipoPessoaDAO.findById(tipoId);
      pessoa.setTipo(tipo);

      pessoa.setSenha(req.getParameter("senha"));

      pessoaDAO.save(pessoa);

      resp.sendRedirect("users");
    }
  }
}
