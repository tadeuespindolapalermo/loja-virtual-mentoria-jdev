package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.enums.TipoPessoa;
import br.com.tadeudeveloper.lojavirtual.model.Endereco;
import br.com.tadeudeveloper.lojavirtual.model.PessoaFisica;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import br.com.tadeudeveloper.lojavirtual.model.dto.CepDTO;
import br.com.tadeudeveloper.lojavirtual.model.dto.ConsultaCnpjDTO;
import br.com.tadeudeveloper.lojavirtual.repository.EnderecoRepository;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaFisicaRepository;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaJuridicaRepository;
import br.com.tadeudeveloper.lojavirtual.service.PessoaUserService;
import br.com.tadeudeveloper.lojavirtual.service.ServiceContagemAcessoApi;
import br.com.tadeudeveloper.lojavirtual.util.ValidaCNPJ;
import br.com.tadeudeveloper.lojavirtual.util.ValidaCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PessoaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ServiceContagemAcessoApi serviceContagemAcessoApi;

    @GetMapping("**/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome) {
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());
        serviceContagemAcessoApi.atualizaAcessoEndpointPF();
        return new ResponseEntity<>(pessoaFisicaList, HttpStatus.OK);
    }

    @GetMapping("**/consultaPfCpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf) {
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.pesquisaPorCpfPF(cpf);
        return new ResponseEntity<>(pessoaFisicaList, HttpStatus.OK);
    }

    @GetMapping("**/consultaNomePJ/{nome}")
    public ResponseEntity<List<PessoaJuridica>> consultaNomePJ(@PathVariable("nome") String nome) {
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase());
        return new ResponseEntity<>(pessoaJuridicaList, HttpStatus.OK);
    }

    @GetMapping("**/consultaCnpjPJ/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaCnpjPJ(@PathVariable("cnpj") String cnpj) {
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaRepository.existeCnpjCadastradoList(cnpj);
        return new ResponseEntity<>(pessoaJuridicaList, HttpStatus.OK);
    }

    @GetMapping("**/consultaCep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {
        return new ResponseEntity<>(pessoaUserService.consultarCep(cep), HttpStatus.OK);
    }

    @GetMapping("**/consultaCnpjReceitaWS/{cnpj}")
    public ResponseEntity<ConsultaCnpjDTO> consultaCnpjReceitaWS(@PathVariable("cnpj") String cnpj) {
        return new ResponseEntity<>(pessoaUserService.consultarCnpjReceitaWS(cnpj), HttpStatus.OK);
    }

    @PostMapping(value = "**/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

        if (pessoaJuridica == null) {
            throw new ExceptionMentoriaJava("Pessoa jurídica não pode ser NULL!");
        }

        if (pessoaJuridica.getTipoPessoa() == null) {
            throw new ExceptionMentoriaJava("Informe o tipo Jurídico ou Fornecedor da Loja.");
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

        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
                montarCep(pessoaJuridica, p);
            }
        } else {
            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
                Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
                if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
                    montarCep(pessoaJuridica, p);
                }
            }
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
        return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);
    }

    @PostMapping(value = "**/salvarPf")
    public ResponseEntity<PessoaFisica> salvarPf(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {

        if (pessoaFisica == null) {
            throw new ExceptionMentoriaJava("Pessoa física não pode ser NULL!");
        }

        if (pessoaFisica.getTipoPessoa() == null) {
            pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
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
    
    private void montarCep(PessoaJuridica pessoaJuridica, int index) {
        CepDTO cepDTO = pessoaUserService.consultarCep(pessoaJuridica.getEnderecos().get(index).getCep());
        pessoaJuridica.getEnderecos().get(index).setBairro(cepDTO.getBairro());
        pessoaJuridica.getEnderecos().get(index).setCidade(cepDTO.getLocalidade());
        pessoaJuridica.getEnderecos().get(index).setComplemento(cepDTO.getComplemento());
        pessoaJuridica.getEnderecos().get(index).setRuaLogra(cepDTO.getLogradouro());
        pessoaJuridica.getEnderecos().get(index).setUf(cepDTO.getUf());
    }
}
