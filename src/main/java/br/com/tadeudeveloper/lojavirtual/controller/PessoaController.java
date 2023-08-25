package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.PessoaFisica;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaFisicaRepository;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaJuridicaRepository;
import br.com.tadeudeveloper.lojavirtual.service.PessoaUserService;
import br.com.tadeudeveloper.lojavirtual.util.ValidaCNPJ;
import br.com.tadeudeveloper.lojavirtual.util.ValidaCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PessoaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @PostMapping(value = "**/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

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

        if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new ExceptionMentoriaJava("CNPJ: " + pessoaJuridica.getCnpj() + " está inválido!");
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

        return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);

    }

    @PostMapping(value = "**/salvarPf")
    public ResponseEntity<PessoaFisica> salvarPf(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {

        if (pessoaFisica == null) {
            throw new ExceptionMentoriaJava("Pessoa física não pode ser NULL!");
        }

        if (pessoaFisica.getId() == null
                && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
            throw new ExceptionMentoriaJava("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
        }

        if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
            throw new ExceptionMentoriaJava("CPF: " + pessoaFisica.getCpf() + " está inválido!");
        }

        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

        return new ResponseEntity<>(pessoaFisica, HttpStatus.OK);

    }
}
