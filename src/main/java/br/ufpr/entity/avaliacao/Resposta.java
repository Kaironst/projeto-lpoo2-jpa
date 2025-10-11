package br.ufpr.entity.avaliacao;

import java.util.List;

import br.ufpr.entity.pessoa.Aluno;
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
public class Resposta {

	private long id;
	private double nota;

	@ManyToOne
	private Avaliacao avaliacao;

	@OneToMany(mappedBy = "avaliacao")
	private List<RespostaQuestao> respostaQuestoes;

	@ManyToOne
	private Aluno aluno;

}
