package br.com.tadeudeveloper.lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;

import java.util.List;

public interface PessoaJuridicaRepository extends CrudRepository<PessoaJuridica, Long> {

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE upper(trim(pj.nome)) LIKE %?1%")
    List<PessoaJuridica> pesquisaPorNomePJ(String nome);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    List<PessoaJuridica> existeCnpjCadastradoList(String cnpj);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscricaoEstadual = ?1")
    PessoaJuridica existeInscEstadualCadastrado(String inscEstadual);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscricaoEstadual = ?1")
    List<PessoaJuridica> existeInscEstadualCadastradoList(String inscEstadual);

}
