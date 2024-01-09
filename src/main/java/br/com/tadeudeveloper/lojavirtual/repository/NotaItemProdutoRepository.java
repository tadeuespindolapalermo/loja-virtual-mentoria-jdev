package br.com.tadeudeveloper.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.tadeudeveloper.lojavirtual.model.NotaItemProduto;

@Transactional
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {
	
	@Query("SELECT a FROM NotaItemProduto a WHERE a.produto.id = ?1 AND a.notaFiscalCompra.id = ?2")
	List<NotaItemProduto> buscarNotaItemPorProdutoNota(Long idProduto, Long idNotaFiscal);
	
	@Query("SELECT a FROM NotaItemProduto a WHERE a.produto.id = ?1")
	List<NotaItemProduto> buscarNotaItemPorProduto(Long idProduto);
	
	@Query("SELECT a FROM NotaItemProduto a WHERE a.notaFiscalCompra.id = ?1")
	List<NotaItemProduto> buscarNotaItemPorNotaFiscal(Long idNotaFiscal);
	
	@Query("SELECT a FROM NotaItemProduto a WHERE a.empresa.id = ?1")
	List<NotaItemProduto> buscarNotaItemPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="delete from nota_item_produto where id = ?1")
	void deleteByIdNotaItem(Long id);

}
