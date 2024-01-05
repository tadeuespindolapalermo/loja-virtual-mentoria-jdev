package br.com.tadeudeveloper.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.tadeudeveloper.lojavirtual.model.NotaFiscalCompra;

@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {
	
	@Query("SELECT a FROM NotaFiscalCompra a WHERE UPPER(TRIM(a.descricaoObservacao)) LIKE %?1%")
	List<NotaFiscalCompra> buscarNotaPorDescricao(String descricao);
	
	@Query(value = "select count(1) > 0 from nota_fiscal_compra where upper(descricao_obs) like %?1%", nativeQuery = true)
	boolean existeNotaComDescricao(String desc);
	
	@Query("SELECT a FROM NotaFiscalCompra a WHERE a.pessoa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorPessoa(Long idPessoa);
	
	@Query("SELECT a FROM NotaFiscalCompra a WHERE a.contaPagar.id = ?1")
	List<NotaFiscalCompra> buscarNotaContaPagar(Long idContaPagar);
	
	@Query("SELECT a FROM NotaFiscalCompra a WHERE a.empresa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "delete from nota_item_produto where nota_fiscal_compra_id = ?1", nativeQuery = true)
	void deleteItemNotaFiscalCompra(Long idNotaFiscalCompra);

}
