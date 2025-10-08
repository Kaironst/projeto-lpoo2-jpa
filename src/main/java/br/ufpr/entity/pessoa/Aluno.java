package br.ufpr.entity.pessoa;

import br.ufpr.entity.curso.Curso;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Aluno extends Pessoa {

	private int matricula, periodo;
	private double ira;

	@ManyToOne
	private Curso curso;

}
