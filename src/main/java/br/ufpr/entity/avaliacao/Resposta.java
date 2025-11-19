package br.ufpr.entity.avaliacao;

import java.util.List;

import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Resposta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private Double nota;

  @ManyToOne
  private Avaliacao avaliacao;

  @OneToMany(mappedBy = "resposta", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RespostaQuestao> respostaQuestoes;

  @ManyToOne
  private Pessoa pessoa;

}
