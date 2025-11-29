package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Questao;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuestaoDAO {

  private final EntityManager em;

  public Questao buscarPorId(long id) {
    return em.find(Questao.class, id);
  }
}
