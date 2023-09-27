package med.voll.api.consulta.valicadoes.agendamento;

import med.voll.api.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

	/* Criando polimorfismo para todos os métodos nomeados VALIDAR */
	void validar (DadosAgendamentoConsulta dados);
		
}
