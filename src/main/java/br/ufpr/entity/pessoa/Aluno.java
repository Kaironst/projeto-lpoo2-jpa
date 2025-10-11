package br.ufpr.entity.pessoa;

import java.util.List;

import br.ufpr.entity.avaliacao.Resposta;
import br.ufpr.entity.curso.Curso;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Aluno extends Pessoa {

	private int matricula, periodo;
	private double ira;

	@ManyToOne
	private Curso curso;

	@OneToMany(mappedBy = "aluno")
	private List<Resposta> respostas;

}
