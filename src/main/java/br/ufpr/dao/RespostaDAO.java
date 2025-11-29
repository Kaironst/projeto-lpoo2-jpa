package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Resposta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RespostaDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public List<Resposta> listarPorPessoa(long pessoaId) {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Resposta> query = em.createQuery(
                    "SELECT r FROM Resposta r WHERE r.pessoa.id = :usuarioId",
                    Resposta.class
            );
            query.setParameter("usuarioId", pessoaId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Resposta buscarRespostaPorAvaliacaoEUsuario(long avaliacaoId, long usuarioId) {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Resposta> query = em.createQuery(
                "select r from Resposta r where r.avaliacao.id = :aval and r.pessoa.id = :user",
                Resposta.class
            );
            query.setParameter("aval", avaliacaoId);
            query.setParameter("user", usuarioId);

            return query.getResultStream().findFirst().orElse(null);

        } finally {
            em.close();
        }
    }

    public void salvar(Resposta resposta) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(resposta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}