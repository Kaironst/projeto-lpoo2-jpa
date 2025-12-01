package br.ufpr.controller;

import br.ufpr.dao.TipoPessoaDAO;
import br.ufpr.entity.pessoa.TipoPessoa;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@WebServlet("/crud/tipos")
public class TipoPessoaServlet extends HttpServlet {

  private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("persistence");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try (var em = EMF.createEntityManager()) {

      var tipoPessoaDAO = new TipoPessoaDAO(em);

      String acao = req.getParameter("acao");
      String idStr = req.getParameter("id");

      if ("editar".equals(acao) && idStr != null) {
        long id = Long.parseLong(idStr);
        TipoPessoa tipoPessoa = tipoPessoaDAO.buscarPorId(id);
        req.setAttribute("tipoEditar", tipoPessoa);

      } else if ("deletar".equals(acao) && idStr != null) {

        long id = Long.parseLong(idStr);
        TipoPessoa tipoPessoa = tipoPessoaDAO.buscarPorId(id);

        if (tipoPessoa != null) {
          tipoPessoaDAO.deletar(tipoPessoa);
        }

        resp.sendRedirect(req.getContextPath() + "/crud/tipos");
        return;
      }

      req.setAttribute("tipos", tipoPessoaDAO.listarTodos());
      req.getRequestDispatcher("/WEB-INF/crud/tipoPessoa.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try (var em = EMF.createEntityManager()) {

      var tipoPessoaDAO = new TipoPessoaDAO(em);

      String idStr = req.getParameter("id");
      String nome = req.getParameter("nome");
      boolean podecriarforms = req.getParameter("podecriarforms") != null;
      boolean poderesponderforms = req.getParameter("poderesponderforms") != null;

      if (idStr != null && !idStr.isEmpty()) {

        long id = Long.parseLong(idStr);
        TipoPessoa tipoPessoa = tipoPessoaDAO.buscarPorId(id);

        if (tipoPessoa != null) {
          tipoPessoa.setNome(nome);
          tipoPessoa.setPodeCriarForms(podecriarforms);
          tipoPessoa.setPodeResponderForms(poderesponderforms);
          tipoPessoaDAO.update(tipoPessoa);
            
        }

      } else {

        TipoPessoa tipoPessoa = new TipoPessoa();
        tipoPessoa.setNome(nome);
        tipoPessoa.setPodeCriarForms(podecriarforms);
        tipoPessoa.setPodeResponderForms(poderesponderforms);
        tipoPessoaDAO.salvar(tipoPessoa);
      }

      resp.sendRedirect(req.getContextPath() + "/crud/tipos");
    }
  }
}
