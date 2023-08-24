package br.com.tadeudeveloper.lojavirtual.repository;

import br.com.tadeudeveloper.lojavirtual.model.PessoaFisica;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
    PessoaFisica existeCpfCadastrado(String cpf);

}
