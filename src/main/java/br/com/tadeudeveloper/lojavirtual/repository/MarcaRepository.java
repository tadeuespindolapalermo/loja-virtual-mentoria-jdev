package br.com.tadeudeveloper.lojavirtual.repository;

import br.com.tadeudeveloper.lojavirtual.model.MarcaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MarcaRepository extends JpaRepository<MarcaProduto, Long> {
	
	@Query("select mp from MarcaProduto mp where upper(trim(mp.nomeDesc)) like %?1%")
	List<MarcaProduto> buscarMarcaProdutoPorNomeDescricao(String nomeDesc);

}
