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
public class Alternativa {

	private long id;
	private int numero;
	private String enunciado;
	private boolean correta;

	@ManyToOne
	private Questao questao;

}
