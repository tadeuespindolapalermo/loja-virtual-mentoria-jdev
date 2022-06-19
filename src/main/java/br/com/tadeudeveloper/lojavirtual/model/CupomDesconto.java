package br.com.tadeudeveloper.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cup_desc")
@SequenceGenerator(name = "seq_cup_desc", sequenceName = "seq_cup_desc", allocationSize = 1, initialValue = 1)
public class CupomDesconto implements Serializable {

	private static final long serialVersionUID = 6379299021984720579L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cup_desc")
	private Long id;

	@Column(name = "cod_desc")
	private String codigoDescricao;

	@Column(name = "valor_real_desc")
	private BigDecimal valorRealDescricao;

	@Column(name = "valor_porcent_desc")
	private BigDecimal valorPorcentualDescricao;

	@Temporal(TemporalType.DATE)
	private Date dataValidadeCupom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoDescricao() {
		return codigoDescricao;
	}

	public void setCodigoDescricao(String codigoDescricao) {
		this.codigoDescricao = codigoDescricao;
	}

	public BigDecimal getValorRealDescricao() {
		return valorRealDescricao;
	}

	public void setValorRealDescricao(BigDecimal valorRealDescricao) {
		this.valorRealDescricao = valorRealDescricao;
	}

	public BigDecimal getValorPorcentualDescricao() {
		return valorPorcentualDescricao;
	}

	public void setValorPorcentualDescricao(BigDecimal valorPorcentualDescricao) {
		this.valorPorcentualDescricao = valorPorcentualDescricao;
	}

	public Date getDataValidadeCupom() {
		return dataValidadeCupom;
	}

	public void setDataValidadeCupom(Date dataValidadeCupom) {
		this.dataValidadeCupom = dataValidadeCupom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupomDesconto other = (CupomDesconto) obj;
		return Objects.equals(id, other.id);
	}

}
