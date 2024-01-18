package br.com.tadeudeveloper.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.tadeudeveloper.lojavirtual.model.AvaliacaoProduto;

@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long>{
	
	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1")
	List<AvaliacaoProduto> avaliacoesProduto(Long idProduto);
	
	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1 and a.pessoa.id = ?2")
	List<AvaliacaoProduto> avaliacoesProdutoPessoa(Long idProduto, Long idPessoa);
	
	@Query(value = "select a from AvaliacaoProduto a where a.pessoa.id = ?1")
	List<AvaliacaoProduto> avaliacoesPessoa(Long idPessoa);

}
