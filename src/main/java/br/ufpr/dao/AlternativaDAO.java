package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Alternativa;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlternativaDAO {

  private final EntityManager em;

  public Alternativa buscarPorId(long id) {
    return em.find(Alternativa.class, id);
  }
}
