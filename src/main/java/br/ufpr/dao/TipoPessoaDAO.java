package br.ufpr.dao;

import java.util.List;

import br.ufpr.entity.pessoa.TipoPessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TipoPessoaDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public List<TipoPessoa> findAll() {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM TipoPessoa t", TipoPessoa.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public TipoPessoa findById(Long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(TipoPessoa.class, id);
        } finally {
            em.close();
        }
    }

    public void save(TipoPessoa tipo) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tipo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(TipoPessoa tipo) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tipo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(TipoPessoa tipo) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            TipoPessoa managed = em.contains(tipo) ? tipo : em.merge(tipo);
            em.remove(managed);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void createDefaultTypesIfEmpty() {
        EntityManager em = EMF.createEntityManager();
        try {
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

        } finally {
            em.close();
        }
    }
}