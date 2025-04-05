package br.com.tadeudeveloper.lojavirtual.model.dto;

import br.com.tadeudeveloper.lojavirtual.model.Endereco;
import br.com.tadeudeveloper.lojavirtual.model.Pessoa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendaCompraLojaVirtualDTO {

	private Long id;
	private BigDecimal valorTotal;
	private BigDecimal valorDesconto;
	private BigDecimal valorFrete;
	private Pessoa pessoa;
	private Endereco cobranca;
	private Endereco entrega;
	private List<ItemVendaDTO> itemVendaLojaList = new ArrayList<>();
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Endereco getCobranca() {
		return cobranca;
	}

	public void setCobranca(Endereco cobranca) {
		this.cobranca = cobranca;
	}

	public Endereco getEntrega() {
		return entrega;
	}

	public void setEntrega(Endereco entrega) {
		this.entrega = entrega;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemVendaDTO> getItemVendaLojaList() {
		return itemVendaLojaList;
	}

	public void setItemVendaLojaList(List<ItemVendaDTO> itemVendaLojaList) {
		this.itemVendaLojaList = itemVendaLojaList;
	}
}
