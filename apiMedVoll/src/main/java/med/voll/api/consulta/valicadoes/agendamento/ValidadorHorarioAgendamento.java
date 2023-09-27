package med.voll.api.consulta.valicadoes.agendamento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import med.voll.api.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAgendamento implements ValidadorAgendamentoDeConsulta{

	public void validar(DadosAgendamentoConsulta dados) {
		
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaEmMinutos = agora.until(dataConsulta, ChronoUnit.MINUTES);
		
		if (diferencaEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser agendada com antencendencia minima de 30 minutos.");
		}
	}
}
