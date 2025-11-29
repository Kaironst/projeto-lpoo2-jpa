package br.ufpr.dao;

import br.ufpr.entity.avaliacao.Avaliacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class AvaliacaoDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public Avaliacao buscarPorId(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(Avaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public List<Avaliacao> listarTodos() {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Avaliacao a", Avaliacao.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void salvar(Avaliacao avaliacao) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(avaliacao);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void atualizar(Avaliacao avaliacao) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.merge(avaliacao);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void deletar(Avaliacao avaliacao) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            Avaliacao managed = em.merge(avaliacao);
            em.remove(managed);
            tx.commit();
        } finally {
            em.close();
        }
    }
}