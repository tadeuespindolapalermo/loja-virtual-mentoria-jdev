package br.com.tadeudeveloper.lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;

public interface PessoaJuridicaRepository extends CrudRepository<PessoaJuridica, Long> {

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);

}
