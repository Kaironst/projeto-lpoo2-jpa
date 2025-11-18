package br.ufpr.entity.avaliacao;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class RespostaQuestao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String respDiscursiva;
  private Boolean correcaoDiscursiva;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Alternativa> respObjetiva;

  @ManyToOne
  private Resposta resposta;

  @ManyToOne
  private Questao questao;

  public Boolean isCorreta() {
    if (this.getQuestao().getTipo() == Questao.Tipo.DISCURSIVA)
      return this.correcaoDiscursiva;
    if (this.getQuestao().getTipo() == Questao.Tipo.OBJETIVA_UNICA)
      return this.getRespObjetiva().get(0).isCorreta();
    for (Alternativa alternativa : this.getRespObjetiva())
      if (!alternativa.isCorreta())
        return false;
    return true;
  }

}
