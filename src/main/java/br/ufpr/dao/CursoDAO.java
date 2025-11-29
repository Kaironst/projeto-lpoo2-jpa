/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.dao;

import br.ufpr.entity.curso.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CursoDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public Curso buscarPorId(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public List<Curso> listarTodos() {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Curso c", Curso.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public void salvar(Curso curso) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(curso);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void atualizar(Curso curso) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.merge(curso);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void deletar(Curso curso) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            Curso managed = em.merge(curso);
            em.remove(managed);
            tx.commit();
        } finally {
            em.close();
        }
    }
}