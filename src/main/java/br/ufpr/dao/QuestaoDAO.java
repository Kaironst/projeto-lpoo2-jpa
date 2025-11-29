package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class QuestaoDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public Questao buscarPorId(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(Questao.class, id);
        } finally {
            em.close();
        }
    }
}