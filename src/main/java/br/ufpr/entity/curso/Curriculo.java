package br.ufpr.entity.curso;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Curriculo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private int periodo;

  @ManyToOne(optional = false)
  private Curso curso;

  @OneToMany(mappedBy = "curriculo", cascade = CascadeType.REMOVE)
  private List<UnidadeCurricular> unidadesCurriculares;

}
