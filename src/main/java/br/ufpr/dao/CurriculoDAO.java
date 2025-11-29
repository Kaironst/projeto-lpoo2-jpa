/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.dao;

import br.ufpr.entity.curso.Curriculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CurriculoDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public Curriculo buscarPorId(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(Curriculo.class, id);
        } finally {
            em.close();
        }
    }

    public List<Curriculo> listarTodos() {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Curriculo c", Curriculo.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void salvar(Curriculo curriculo) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(curriculo);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void atualizar(Curriculo curriculo) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.merge(curriculo);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void deletar(Curriculo curriculo) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            Curriculo managed = em.merge(curriculo);
            em.remove(managed);
            tx.commit();
        } finally {
            em.close();
        }
    }
}