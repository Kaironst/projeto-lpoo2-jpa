package br.ufpr.dao;

import java.util.List;

import br.ufpr.entity.pessoa.TipoPessoa;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TipoPessoaDAO {

  private final EntityManager em;

  public List<TipoPessoa> findAll() {
    return em.createQuery("SELECT t FROM TipoPessoa t", TipoPessoa.class)
        .getResultList();
  }

  public TipoPessoa findById(Long id) {
    return em.find(TipoPessoa.class, id);
  }

  public void save(TipoPessoa tipo) {
    em.getTransaction().begin();
    em.persist(tipo);
    em.getTransaction().commit();
  }

  public void update(TipoPessoa tipo) {
    em.getTransaction().begin();
    em.merge(tipo);
    em.getTransaction().commit();
  }

  public void delete(TipoPessoa tipo) {
    em.getTransaction().begin();
    TipoPessoa managed = em.contains(tipo) ? tipo : em.merge(tipo);
    em.remove(managed);
    em.getTransaction().commit();
  }

  public void createDefaultTypesIfEmpty() {
    long count = em.createQuery("SELECT COUNT(t) FROM TipoPessoa t", Long.class)
        .getSingleResult();

    if (count == 0) {
      em.getTransaction().begin();
      em.persist(new TipoPessoa("aluno", false, true));
      em.persist(new TipoPessoa("professor", true, true));
      em.persist(new TipoPessoa("coordenador", true, true));
      em.persist(new TipoPessoa("administrador", true, true));
      em.getTransaction().commit();
    }

  }
}
