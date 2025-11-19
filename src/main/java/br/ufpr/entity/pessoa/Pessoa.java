package br.ufpr.entity.pessoa;

import java.util.List;

import br.ufpr.entity.curso.Curso;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

  private String nome, email, cpf, matricula;

  private Integer período;

  @ManyToOne
  private TipoPessoa tipo;

  @ManyToMany
  private List<Curso> curso;

}
