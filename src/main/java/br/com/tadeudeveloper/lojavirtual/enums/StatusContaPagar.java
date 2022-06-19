package br.com.tadeudeveloper.lojavirtual.enums;

public enum StatusContaPagar {

	COBRANCA("Cobrança"), 
	VENCIDA("Vencida"), 
	ABERTA("Aberta"), 
	QUITADA("Quitada"),
	ALUGUEL("Aluguel"), 
	FUNCIONARIO("Funcionário"), 
	NEGOCIADA("Renegociada");

	private String descricao;

	private StatusContaPagar(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

}
