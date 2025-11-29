/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.dao;

import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PessoaDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("persistence");

    public Pessoa buscarPorEmailSenha(String email, String senha) {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Pessoa> query = em.createQuery(
                    "SELECT p FROM Pessoa p WHERE p.email = :email AND p.senha = :senha",
                    Pessoa.class
            );
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
    
    public Pessoa findById(long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pessoa> findAll() {
        EntityManager em = EMF.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Pessoa pessoa) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.persist(pessoa);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void update(Pessoa pessoa) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            em.merge(pessoa);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void delete(Pessoa pessoa) {
        EntityManager em = EMF.createEntityManager();
        try {
            var tx = em.getTransaction();
            tx.begin();
            Pessoa managed = em.merge(pessoa);
            em.remove(managed);
            tx.commit();
        } finally {
            em.close();
        }
    }
}