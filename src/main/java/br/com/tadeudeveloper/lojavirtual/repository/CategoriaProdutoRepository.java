package br.com.tadeudeveloper.lojavirtual.repository;

import br.com.tadeudeveloper.lojavirtual.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    @Query(value = "select count(1) > 0 from categoria_produto where upper(trim(nome_desc)) = upper(trim(?1))", nativeQuery = true)
    boolean existeCategoria(String nomeCategoria);

    @Query("select c from CategoriaProduto c where upper(trim(c.nomeDesc)) like %?1%")
    List<CategoriaProduto> buscarCategoriaDesc(String nomeDesc);
}
