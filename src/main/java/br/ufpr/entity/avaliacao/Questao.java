package br.ufpr.entity.avaliacao;

import java.util.List;

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
public class Questao {

	static enum Tipo {
		DISCURSIVA,
		OBJETIVA_UNICA,
		OBJETIVA_MULTIPLA
	}

	private long id;
	private String enunciado;
	private float valor;
	private Tipo tipo;

	@OneToMany(mappedBy = "questao")
	private List<RespostaQuestao> respostasQuestao;

	@ManyToOne
	private Avaliacao avaliacao;

	@OneToMany(mappedBy = "questao")
	private List<Alternativa> alternativas;

}
