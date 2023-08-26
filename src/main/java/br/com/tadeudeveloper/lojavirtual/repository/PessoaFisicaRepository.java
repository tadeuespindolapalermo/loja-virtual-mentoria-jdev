package br.com.tadeudeveloper.lojavirtual.repository;

import br.com.tadeudeveloper.lojavirtual.model.PessoaFisica;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE upper(trim(pf.nome)) LIKE %?1%")
    List<PessoaFisica> pesquisaPorNomePF(String nome);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    List<PessoaFisica> pesquisaPorCpfPF(String cpf);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    PessoaFisica existeCpfCadastrado(String cpf);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    List<PessoaFisica> existeCpfCadastradoList(String cpf);



}
