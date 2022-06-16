package br.com.tadeudeveloper.lojavirtual.enums;

public enum TipoEndereco {
	
	COBRANCA("Cobrança"),
	ENTREGA("Entrega");
	
	private String descricao;

	private TipoEndereco(String descricao) {
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
