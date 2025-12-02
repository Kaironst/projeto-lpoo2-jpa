package br.ufpr.entity.pessoa;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class TipoPessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  private String nome;

  @OneToMany(mappedBy = "tipo")
  private List<Pessoa> pessoas;

  private Boolean podeCriarForms;
  private Boolean podeResponderForms;

  public TipoPessoa(String nome, boolean podeCriarForms, boolean podeResponderForms) {
    this.nome = nome;
    this.podeCriarForms = podeCriarForms;
    this.podeResponderForms = podeResponderForms;
  }

  @Override
  public String toString() {
    return "TipoPessoa{id=" + id + ", nome=" + nome + "}";
  }
}
