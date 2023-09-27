package med.voll.api.consulta.valicadoes.agendamento;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoDeConsulta {
	
	public void validar(DadosAgendamentoConsulta dados) {
		
		var dataConsulta = dados.data();
		
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var antesDoHorarioDaClinica = dataConsulta.getHour() < 7;
		var depoisDoHorarioDaClinica = dataConsulta.getHour() > 18;
		
		if (domingo || antesDoHorarioDaClinica || depoisDoHorarioDaClinica) {
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica.");
		}
	}
	
}
