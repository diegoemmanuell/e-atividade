package br.com.eatividade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.eatividade.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long>{

	@Query("select t from Turma t order by t.id")
	List<Turma> findAllOrderById();

}
