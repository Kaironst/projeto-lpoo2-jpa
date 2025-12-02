package br.ufpr.entity.avaliacao;

import java.util.List;

import br.ufpr.entity.curso.UnidadeCurricular;
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
public class Avaliacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private boolean isAberta;
  private boolean isAnon;

  @ManyToOne(cascade = CascadeType.ALL)
  private Pessoa dono;

  @ManyToOne
  private UnidadeCurricular unidadeCurricular;

  @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Questao> questoes;

  @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL)
  private List<Resposta> resposta;

}
