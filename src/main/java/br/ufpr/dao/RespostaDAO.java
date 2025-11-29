package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Resposta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RespostaDAO {

  private final EntityManager em;

  public List<Resposta> listarPorPessoa(long pessoaId) {
    TypedQuery<Resposta> query = em.createQuery(
        "SELECT r FROM Resposta r WHERE r.pessoa.id = :usuarioId",
        Resposta.class);
    query.setParameter("usuarioId", pessoaId);
    return query.getResultList();
  }

  public Resposta buscarRespostaPorAvaliacaoEUsuario(long avaliacaoId, long usuarioId) {
    TypedQuery<Resposta> query = em.createQuery(
        "select r from Resposta r where r.avaliacao.id = :aval and r.pessoa.id = :user",
        Resposta.class);
    query.setParameter("aval", avaliacaoId);
    query.setParameter("user", usuarioId);

    return query.getResultStream().findFirst().orElse(null);

  }

  public void salvar(Resposta resposta) {
    em.getTransaction().begin();
    em.persist(resposta);
    em.getTransaction().commit();
  }
}
