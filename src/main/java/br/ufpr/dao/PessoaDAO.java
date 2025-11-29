/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.dao;

import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PessoaDAO {

  private final EntityManager em;

  public Pessoa buscarPorEmailSenha(String email, String senha) {
    TypedQuery<Pessoa> query = em.createQuery(
        "SELECT p FROM Pessoa p WHERE p.email = :email AND p.senha = :senha",
        Pessoa.class);
    query.setParameter("email", email);
    query.setParameter("senha", senha);

    return query.getResultStream().findFirst().orElse(null);
  }

  public Pessoa findById(long id) {
    return em.find(Pessoa.class, id);
  }

  public List<Pessoa> findAll() {
    return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class)
        .getResultList();
  }

  public void save(Pessoa pessoa) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(pessoa);
    tx.commit();
  }

  public void update(Pessoa pessoa) {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(pessoa);
    tx.commit();
  }

  public void delete(Pessoa pessoa) {
    var tx = em.getTransaction();
    tx.begin();
    Pessoa managed = em.merge(pessoa);
    em.remove(managed);
    tx.commit();
  }
}
