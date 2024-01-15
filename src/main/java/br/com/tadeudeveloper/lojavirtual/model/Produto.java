package br.com.tadeudeveloper.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {

	private static final long serialVersionUID = 3282407769484058814L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;

	@NotNull(message = "O tipo de unidade deve ser informado.")
	@Column(nullable = false)
	private String tipoUnidade;

	@Size(min = 10, message = "Nome do produto deve ter mais de 10 letras.")
	@NotNull(message = "Nome do produto deve ser informado.")
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;

	@NotNull(message = "Descrição deve ser informada.")
	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String descricao;

	// TODO NotaItemProduto

	@NotNull(message = "Peso deve ser informado.")
	@Column(nullable = false)
	private Double peso;

	@NotNull(message = "Largura deve ser informada.")
	@Column(nullable = false)
	private Double largura;

	@NotNull(message = "Altura deve ser informada.")
	@Column(nullable = false)
	private Double altura;

	@NotNull(message = "Profundidade deve ser informada.")
	@Column(nullable = false)
	private Double profundidade;

	@NotNull(message = "Valor da venda deve ser informada.")
	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;

	@Column(name = "qtd_estoque", nullable = false)
	private Integer quantidadeEstoque = 0;

	@Column(name = "qtd_alerta_estoque")
	private Integer quantidadeAlertaEstoque = 0;

	private String linkVideoYouTube;

	@Column(name = "alerta_qtd_estoque")
	private Boolean alertaQuantidadeEstoque = Boolean.FALSE;

	@Column(name = "qtd_clique")
	private Integer quantidadeClique = 0;

	@NotNull(message = "Empresa responsável deve ser informada.")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;

	@NotNull(message = "A categoria do produto deve ser informada.")
	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categoria_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_produto_id_fk"))
	private CategoriaProduto categoriaProduto;

	@NotNull(message = "A marca do produto deve ser informada.")
	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marca_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marca_produto_id_fk"))
	private MarcaProduto marcaProduto;
	
	@OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ImagemProduto> imagens = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Integer getQuantidadeAlertaEstoque() {
		return quantidadeAlertaEstoque;
	}

	public void setQuantidadeAlertaEstoque(Integer quantidadeAlertaEstoque) {
		this.quantidadeAlertaEstoque = quantidadeAlertaEstoque;
	}

	public String getLinkVideoYouTube() {
		return linkVideoYouTube;
	}

	public void setLinkVideoYouTube(String linkVideoYouTube) {
		this.linkVideoYouTube = linkVideoYouTube;
	}

	public Boolean getAlertaQuantidadeEstoque() {
		return alertaQuantidadeEstoque;
	}

	public void setAlertaQuantidadeEstoque(Boolean alertaQuantidadeEstoque) {
		this.alertaQuantidadeEstoque = alertaQuantidadeEstoque;
	}

	public Integer getQuantidadeClique() {
		return quantidadeClique;
	}

	public void setQuantidadeClique(Integer quantidadeClique) {
		this.quantidadeClique = quantidadeClique;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}	

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public MarcaProduto getMarcaProduto() {
		return marcaProduto;
	}

	public void setMarcaProduto(MarcaProduto marcaProduto) {
		this.marcaProduto = marcaProduto;
	}
	
	public List<ImagemProduto> getImagens() {
		return imagens;
	}
	
	public void setImagens(List<ImagemProduto> imagens) {
		this.imagens = imagens;
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

}
