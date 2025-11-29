/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.dao;

import br.ufpr.entity.curso.Curriculo;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CurriculoDAO {

  private final EntityManager em;

  public Curriculo buscarPorId(long id) {
    return em.find(Curriculo.class, id);
  }

  public List<Curriculo> listarTodos() {
    return em.createQuery("SELECT c FROM Curriculo c", Curriculo.class)
        .getResultList();
  }

  public void salvar(Curriculo curriculo) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(curriculo);
    tx.commit();
  }

  public void atualizar(Curriculo curriculo) {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(curriculo);
    tx.commit();
  }

  public void deletar(Curriculo curriculo) {
    var tx = em.getTransaction();
    tx.begin();
    Curriculo managed = em.merge(curriculo);
    em.remove(managed);
    tx.commit();
  }
}
