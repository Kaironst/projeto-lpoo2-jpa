package br.ufpr.entity.curso;

import java.util.List;

import br.ufpr.entity.pessoa.Pessoa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private int numPeriodos;

    @ManyToMany(mappedBy = "curso")
    private List<Pessoa> pessoas;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE)
    private List<Curriculo> curriculos;
    
}