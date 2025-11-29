package br.ufpr.dao;

import br.ufpr.entity.curso.UnidadeCurricular;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UnidadeCurricularDAO {

  private final EntityManager em;

  public UnidadeCurricular buscarPorId(long id) {
    return em.find(UnidadeCurricular.class, id);
  }

  public List<UnidadeCurricular> listarTodos() {
    return em.createQuery("SELECT u FROM UnidadeCurricular u", UnidadeCurricular.class)
        .getResultList();
  }

  public void salvar(UnidadeCurricular unidade) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(unidade);
    tx.commit();
  }

  public void atualizar(UnidadeCurricular unidade) {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(unidade);
    tx.commit();
  }

  public void deletar(UnidadeCurricular unidade) {
    var tx = em.getTransaction();
    tx.begin();
    UnidadeCurricular managed = em.merge(unidade);
    em.remove(managed);
    tx.commit();
  }
}
