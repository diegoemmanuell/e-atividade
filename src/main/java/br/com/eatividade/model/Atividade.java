package br.com.eatividade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Atividade {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ATIVIDADE")
	@SequenceGenerator(name = "SQ_ATIVIDADE", sequenceName = "SQ_ATIVIDADE", initialValue = 1, allocationSize = 1)
	@Column(length = 4)
	private @Getter @Setter Long id;
	
	@Column(nullable = false, length = 100)
	private @Getter @Setter String nome;
	
	@Column(nullable = false, length = 30)
	private @Getter @Setter String materia;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private @Getter @Setter Turma turma;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private @Getter @Setter Date dataCadastro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private @Getter @Setter Date dataDevolucao;
	
	private @Getter @Setter String urlVideo;
	
	private @Getter @Setter byte[] pdf;
	
}
