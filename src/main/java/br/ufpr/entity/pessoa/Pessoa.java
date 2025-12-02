package br.ufpr.entity.pessoa;

import java.util.List;

import br.ufpr.entity.curso.Curso;
import br.ufpr.entity.curso.UnidadeCurricular;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nome, email, cpf;
  private String senha;
  private Integer periodo;

  @ManyToOne
  private TipoPessoa tipo;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "pessoa_curso",
    joinColumns = @JoinColumn(name = "pessoa_id"),
    inverseJoinColumns = @JoinColumn(name = "curso_id")
)
  private List<Curso> curso;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "pessoa_unidadecurricular",
    joinColumns = @JoinColumn(name = "pessoa_id"),
    inverseJoinColumns = @JoinColumn(name = "atividades_id")
)
  private List<UnidadeCurricular> atividades;

  @Override
  public String toString() {
    return "Pessoa{id=" + id + ", nome=" + nome + "}";
  }
  
  public void addAtividade(UnidadeCurricular atividade) {
    if (atividades == null) atividades = new ArrayList<>();
    if (!atividades.contains(atividade)) atividades.add(atividade);

    if (atividade.getPessoas() == null) atividade.setPessoas(new ArrayList<>());
    if (!atividade.getPessoas().contains(this)) atividade.getPessoas().add(this);
}

  public void setAtividades(List<UnidadeCurricular> listaAtividades) {
    this.atividades = new ArrayList<>();
    if (listaAtividades != null) {
        for (UnidadeCurricular u : listaAtividades) {
            addAtividade(u); // usa addAtividade para manter bidirecional
        }
    }
}
  public void setCurso(List<Curso> cursos) {
    this.curso = cursos;

    if (cursos != null) {
        for (Curso c : cursos) {
            if (c.getPessoas() == null) {
                c.setPessoas(new ArrayList<>());
            }
            if (!c.getPessoas().contains(this)) {
                c.getPessoas().add(this);
            }
        }
    }
}
}
