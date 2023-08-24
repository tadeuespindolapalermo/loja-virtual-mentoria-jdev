package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaJuridicaRepository;
import br.com.tadeudeveloper.lojavirtual.service.PessoaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @PostMapping(value = "**/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

        if (pessoaJuridica == null) {
            throw new ExceptionMentoriaJava("Pessoa jurídica não pode ser NULL!");
        }

        if (pessoaJuridica.getId() == null
                && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null
                && pessoaJuridicaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscricaoEstadual()) != null) {
            throw new ExceptionMentoriaJava("Já existe Inscrição Estadual cadastrads com o número: " + pessoaJuridica.getInscricaoEstadual());
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

        return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);

    }
}
