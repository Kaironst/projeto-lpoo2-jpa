/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.dao;

import br.ufpr.entity.curso.Curso;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CursoDAO {

  private final EntityManager em;

  public Curso buscarPorId(long id) {
    return em.find(Curso.class, id);
  }

  public List<Curso> listarTodos() {
    return em.createQuery("SELECT c FROM Curso c", Curso.class)
        .getResultList();
  }

  public void salvar(Curso curso) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(curso);
    tx.commit();
  }

  public void atualizar(Curso curso) {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(curso);
    tx.commit();
  }

  public void deletar(Curso curso) {
    var tx = em.getTransaction();
    tx.begin();
    Curso managed = em.merge(curso);
    em.remove(managed);
    tx.commit();
  }
}
