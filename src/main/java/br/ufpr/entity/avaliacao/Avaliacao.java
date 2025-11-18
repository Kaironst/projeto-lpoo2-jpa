package br.ufpr.entity.avaliacao;

import java.util.List;

import br.ufpr.entity.curso.UnidadeCurricular;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Avaliacao {

	private long id;
	private boolean isAberta;
	private boolean isAnon;

	@ManyToOne
	private UnidadeCurricular unidadeCurricular;

	@OneToMany(mappedBy = "avaliacao")
	private List<Questao> questoes;

	@ManyToOne
	private Resposta resposta;

}
