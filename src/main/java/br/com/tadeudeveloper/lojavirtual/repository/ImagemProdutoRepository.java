package br.com.tadeudeveloper.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.tadeudeveloper.lojavirtual.model.ImagemProduto;

@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {
	
	@Query("select a from ImagemProduto a where a.produto.id = ?1")
	List<ImagemProduto> buscarImagensProduto(Long idProduto);
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(value = "delete from imagem_produto where produto_id = ?1", nativeQuery = true)
	void deletarTodasImagensDoProduto(Long idProduto);

}
