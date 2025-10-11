package br.ufpr.entity.avaliacao;

import java.util.List;

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
public class Questao {

	private long id;
	private String enunciado;
	private float valor;
	private int tipo;
	private int attribute;

	@OneToMany(mappedBy = "questao")
	private List<RespostaQuestao> respostasQuestao;

	@ManyToOne
	private Avaliacao avaliacao;

	@OneToMany(mappedBy = "questao")
	private List<Alternativa> alternativas;

}
