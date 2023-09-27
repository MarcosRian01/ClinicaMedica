package med.voll.api.consulta;

public record DadosDetalhamentoCancelar(
		Long id, 
		MotivoCancelamento motivo
		) {

	public DadosDetalhamentoCancelar(Consulta consulta) {
		this(consulta.getId(), consulta.getMotivoCancelamento());
	} 

}
