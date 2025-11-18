package br.ufpr.entity.pessoa;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TipoPessoa {

	long id;

	@OneToMany(mappedBy = "tipo")
	private List<Pessoa> pessoas;

	private Boolean podeCriarForms;
	private Boolean podeResponderForms;

}
