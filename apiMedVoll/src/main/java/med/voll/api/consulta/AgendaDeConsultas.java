package med.voll.api.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.consulta.valicadoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
	
	@Autowired
	private ConsultaRepository repository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	/* Injetando os validadores por meio de List */
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;
	
	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Id do paciente informado não existe!");
		}
		
		if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("Id do médico informado não existe!");
		}
		
		/* Loop para percorrer todos os validadores */
		validadores.forEach(v -> v.validar(dados));
		
		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var medico = escolherMedico(dados);
		if (medico == null) {
			throw new ValidacaoException("Não existe médico disponivel para esta data!");
		}
		var consulta = new Consulta(null, medico, paciente, dados.data(), null);
		
		repository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if (dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if (dados.especialidade() == null) {
			throw new ValidacaoException("A especialidade do médico é obrigatória!");
		}
		
		return medicoRepository.escolherMedicoAleatorio(dados.especialidade(), dados.data());
	}

	public DadosDetalhamentoCancelar cancelar(@Valid DadosCancelamentoConsulta dados) {
		if (!repository.existsById(dados.id())) {
			throw new ValidacaoException("Id da consulta informada não existe!");
		}
		
		validadoresCancelamento.forEach(v -> v.validar(dados));
		
		var consulta = repository.getReferenceById(dados.id());
		consulta.cancelar(dados.motivo());
		
		return new DadosDetalhamentoCancelar(consulta);
	}
	
}
