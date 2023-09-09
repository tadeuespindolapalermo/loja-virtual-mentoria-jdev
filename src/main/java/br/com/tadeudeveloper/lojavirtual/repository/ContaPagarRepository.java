package br.com.tadeudeveloper.lojavirtual.repository;

import br.com.tadeudeveloper.lojavirtual.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

	@Query("SELECT cp FROM ContaPagar cp WHERE upper(trim(cp.descricao)) LIKE %?1%")
	List<ContaPagar> buscarContaPorDescricao(String desc);

	@Query("SELECT cp FROM ContaPagar cp WHERE cp.pessoa.id = ?1")
	List<ContaPagar> buscarContaPorPessoa(Long idPessoa);

	@Query("SELECT cp FROM ContaPagar cp WHERE cp.pessoaFornecedor.id = ?1")
	List<ContaPagar> buscarContaPorFornecedor(Long idFornecedor);

	@Query("SELECT cp FROM ContaPagar cp WHERE cp.empresa.id = ?1")
	List<ContaPagar> buscarContaPorEmpresa(Long idEmpresa);
	
}
