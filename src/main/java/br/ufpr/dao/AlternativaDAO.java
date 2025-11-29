package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Alternativa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AlternativaDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public Alternativa buscarPorId(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(Alternativa.class, id);
        } finally {
            em.close();
        }
    }
}