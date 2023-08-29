package br.com.tadeudeveloper.lojavirtual.repository;

import br.com.tadeudeveloper.lojavirtual.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1))", nativeQuery = true)
    boolean existeProduto(String nomeProduto);

    @Query(value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1)) and empresa_id = ?2", nativeQuery = true)
    boolean existeProduto(String nomeProduto, Long idEmpresa);

    @Query("select p from Produto p where upper(trim(p.nome)) like %?1%")
    List<Produto> buscarProdutoNome(String nome);

    @Query("select p from Produto p where upper(trim(p.nome)) like %?1% and p.empresa.id = ?2")
    List<Produto> buscarProdutoNome(String nome, Long idEmpresa);
}
