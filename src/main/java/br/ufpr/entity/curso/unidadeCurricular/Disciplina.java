package br.ufpr.entity.curso.unidadeCurricular;

import java.util.List;

import br.ufpr.entity.avaliacao.Avaliacao;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Disciplina extends UnidadeCurricular {

	@OneToMany(mappedBy = "disciplina")
	private List<Avaliacao> avaliacoes;

}
