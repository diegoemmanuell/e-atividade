package br.com.eatividade.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eatividade.model.Turma;
import br.com.eatividade.repository.TurmaRepository;

@Service
public class TurmaService {

	@Autowired private TurmaRepository turmaRepository;
	public List<Turma> findAllOrderById() {
		return turmaRepository.findAllOrderById();
	}
	public Turma salva(Turma turma) {
		return turmaRepository.save(turma);
	}
	public Turma findById(Long idTurma) {
		
		Optional<Turma> turmaOpt = turmaRepository.findById(idTurma);
		
		return turmaOpt.isPresent() ? turmaOpt.get() : new Turma();
	}
}
