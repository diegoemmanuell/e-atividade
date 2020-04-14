package br.com.eatividade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.eatividade.model.Atividade;
import br.com.eatividade.model.Turma;

public interface AtividadeRepository extends JpaRepository<Atividade, Long>{


	@Query("select a from Atividade a where a.turma = ?1 or ?1 is null order by a.dataDevolucao, a.id")
	List<Atividade> findAllOrderByDataDevolucaoAndId(Turma turma);

}
