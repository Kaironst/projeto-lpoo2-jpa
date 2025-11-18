package br.ufpr.entity.curso;

import java.util.List;

import br.ufpr.entity.avaliacao.Avaliacao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class UnidadeCurricular {

	static enum Tipo {
		DISCIPLINA,
		ATIVIDADE_FORMATIVA,
		ATIVIDADE_EXTENSAO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;
	private String descricao;

	@OneToMany(mappedBy = "unidadeCurricular")
	private List<Avaliacao> avaliacoes;

}
