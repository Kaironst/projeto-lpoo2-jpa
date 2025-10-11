package br.ufpr.entity.avaliacao;

import java.util.List;

import br.ufpr.entity.curso.unidadeCurricular.Disciplina;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Avaliacao {

	private long id;
	private boolean isAberta;
	private boolean isAnon;

	@ManyToOne
	private Disciplina disciplina;

	@OneToMany(mappedBy = "avaliacao")
	private List<Questao> questoes;

	@ManyToOne
	private Resposta resposta;

}
