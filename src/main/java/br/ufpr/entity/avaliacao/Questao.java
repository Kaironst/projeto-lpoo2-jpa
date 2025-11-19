package br.ufpr.entity.avaliacao;

import java.util.List;

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
public class Questao {

  public static enum Tipo {
    DISCURSIVA,
    OBJETIVA_UNICA,
    OBJETIVA_MULTIPLA
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String enunciado;
  private float valor;
  private Tipo tipo;

  @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RespostaQuestao> respostasQuestao;

  @ManyToOne
  private Avaliacao avaliacao;

  @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Alternativa> alternativas;

}
