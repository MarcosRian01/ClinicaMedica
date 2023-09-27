package med.voll.api.consulta.valicadoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.consulta.ConsultaRepository;
import med.voll.api.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorConflitoHorarioDeConsulta implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private ConsultaRepository repository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		var medicoPossuiOutraConsultaMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
		
		if(medicoPossuiOutraConsultaMesmoHorario) {
			throw new ValidacaoException("Médico já possui outra consulta agendada neste mesmo horário.");
		}
	}
	
}
