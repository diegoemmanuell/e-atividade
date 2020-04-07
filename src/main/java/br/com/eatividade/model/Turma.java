package br.com.eatividade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TURMA")
	@SequenceGenerator(name = "SQ_TURMA", sequenceName = "SQ_TURMA", initialValue = 1, allocationSize = 1)
	private @Getter @Setter Long id;
	
	@Column(nullable = false, length = 20)
	private @Getter @Setter String nome;
	
	@Column(nullable = false, length = 10)
	private @Getter @Setter String turno;
	
}
