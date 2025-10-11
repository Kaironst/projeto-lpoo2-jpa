package br.ufpr.entity.avaliacao;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class RespostaQuestao {

	private long id;
	private String respDiscursiva;
	private int respObjetiva;

	@ManyToOne
	private Resposta resposta;

	@ManyToOne
	private Questao questao;

	public boolean isCorreta() {
		// TODO - implement RespostaQuestao.isCorreta
		throw new UnsupportedOperationException();
	}

}
