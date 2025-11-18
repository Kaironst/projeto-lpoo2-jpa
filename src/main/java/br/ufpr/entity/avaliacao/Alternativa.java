package br.ufpr.entity.avaliacao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Alternativa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private int numero;
  private String enunciado;
  private boolean correta;

  @ManyToOne
  private Questao questao;

}
