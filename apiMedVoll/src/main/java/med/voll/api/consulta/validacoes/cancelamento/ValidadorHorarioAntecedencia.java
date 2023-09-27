package med.voll.api.consulta.validacoes.cancelamento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.consulta.ConsultaRepository;
import med.voll.api.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosCancelamentoConsulta dados) {
		var dataConsulta = repository.getReferenceById(dados.id());
		var agora = LocalDateTime.now();
		var diferencaEmMinutos = agora.until(dataConsulta.getData(), ChronoUnit.HOURS);
		
		if (diferencaEmMinutos < 24) {
			throw new ValidacaoException("Consulta somente pode ser cancelada com antecedencia minima de 24 horas.");
		}
	}
	
}
