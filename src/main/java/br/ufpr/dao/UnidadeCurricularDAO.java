package br.ufpr.dao;

import br.ufpr.entity.curso.UnidadeCurricular;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class UnidadeCurricularDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public UnidadeCurricular buscarPorId(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(UnidadeCurricular.class, id);
        } finally {
            em.close();
        }
    }

    public List<UnidadeCurricular> listarTodos() {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM UnidadeCurricular u", UnidadeCurricular.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void salvar(UnidadeCurricular unidade) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(unidade);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void atualizar(UnidadeCurricular unidade) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.merge(unidade);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void deletar(UnidadeCurricular unidade) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            UnidadeCurricular managed = em.merge(unidade);
            em.remove(managed);
            tx.commit();
        } finally {
            em.close();
        }
    }
}