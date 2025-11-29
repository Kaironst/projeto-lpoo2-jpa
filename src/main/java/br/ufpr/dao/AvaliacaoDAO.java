package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Avaliacao;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AvaliacaoDAO {

  private EntityManager em;

  public Avaliacao buscarPorId(long id) {
    return em.find(Avaliacao.class, id);
  }

  public List<Avaliacao> listarTodos() {
    return em.createQuery("SELECT a FROM Avaliacao a", Avaliacao.class)
        .getResultList();
  }

  public void salvar(Avaliacao avaliacao) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(avaliacao);
    tx.commit();
  }

  public void atualizar(Avaliacao avaliacao) {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(avaliacao);
    tx.commit();
  }

  public void deletar(Avaliacao avaliacao) {
    var tx = em.getTransaction();
    tx.begin();
    Avaliacao managed = em.merge(avaliacao);
    em.remove(managed);
    tx.commit();
  }
}
