package br.ufpr.dao;

import br.ufpr.entity.curso.Curso;
import br.ufpr.entity.curso.UnidadeCurricular;
import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PessoaDAO {

    private final EntityManager em;

    public Pessoa buscarPorEmailSenha(String email, String senha) {
        TypedQuery<Pessoa> query = em.createQuery(
                "SELECT p FROM Pessoa p WHERE p.email = :email AND p.senha = :senha",
                Pessoa.class);
        query.setParameter("email", email);
        query.setParameter("senha", senha);
        return query.getResultStream().findFirst().orElse(null);
    }

    public Pessoa findById(long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> findAll() {
        return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
    }

    public void saveOrUpdate(Pessoa pessoa) {
        var tx = em.getTransaction();
        tx.begin();

        // Garantir que todos os cursos estejam managed
        if (pessoa.getCurso() != null) {
            List<Curso> managedCursos = new ArrayList<>();
            for (Curso c : pessoa.getCurso()) {
                Curso managed = em.find(Curso.class, c.getId());
                if (managed != null) managedCursos.add(managed);
            }
            pessoa.setCurso(managedCursos);
        }

        // Garantir que todas as atividades estejam managed
        if (pessoa.getAtividades() != null) {
            List<UnidadeCurricular> managedAtividades = new ArrayList<>();
            for (UnidadeCurricular a : pessoa.getAtividades()) {
                UnidadeCurricular managed = em.find(UnidadeCurricular.class, a.getId());
                if (managed != null) managedAtividades.add(managed);
            }
            pessoa.setAtividades(managedAtividades);
        }

        // Atualiza lado inverso das relações bidirecionais
        syncManyToMany(pessoa);

        if (pessoa.getId() == 0) {
            em.persist(pessoa);
        } else {
            em.merge(pessoa);
        }

        tx.commit();
    }

    public void delete(Pessoa pessoa) {
        var tx = em.getTransaction();
        tx.begin();
        Pessoa managed = em.merge(pessoa);
        em.remove(managed);
        tx.commit();
    }

    /** Mantém consistência bidirecional ManyToMany */
    private void syncManyToMany(Pessoa pessoa) {
        if (pessoa.getCurso() != null) {
            for (Curso c : pessoa.getCurso()) {
                if (c.getPessoas() == null) c.setPessoas(new ArrayList<>());
                if (!c.getPessoas().contains(pessoa)) c.getPessoas().add(pessoa);
            }
        }

        if (pessoa.getAtividades() != null) {
            for (UnidadeCurricular a : pessoa.getAtividades()) {
                if (a.getPessoas() == null) a.setPessoas(new ArrayList<>());
                if (!a.getPessoas().contains(pessoa)) a.getPessoas().add(pessoa);
            }
        }
    }
}
