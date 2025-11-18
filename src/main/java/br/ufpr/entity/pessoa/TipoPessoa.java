package br.ufpr.entity.pessoa;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TipoPessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @OneToMany(mappedBy = "tipo")
  private List<Pessoa> pessoas;

  private Boolean podeCriarForms;
  private Boolean podeResponderForms;

}
