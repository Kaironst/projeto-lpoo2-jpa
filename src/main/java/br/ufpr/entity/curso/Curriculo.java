package br.ufpr.entity.curso;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

}
