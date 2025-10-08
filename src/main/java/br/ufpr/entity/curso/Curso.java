package br.ufpr.entity.curso;

import java.util.List;

import br.ufpr.entity.pessoa.Aluno;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;
	private int numPeriodos;

	@OneToMany(mappedBy = "curso")
	private List<Aluno> alunos;

	@OneToMany(mappedBy = "curso")
	private List<Curriculo> curriculos;
}
