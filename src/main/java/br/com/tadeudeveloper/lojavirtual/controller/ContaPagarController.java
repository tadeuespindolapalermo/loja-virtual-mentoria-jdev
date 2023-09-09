package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.ContaPagar;
import br.com.tadeudeveloper.lojavirtual.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContaPagarController {

	@Autowired
	private ContaPagarRepository contaPagarRepository;

	@PostMapping(value = "**/salvarContaPagar")
	public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionMentoriaJava {
		if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Empresa responsável deve ser informada!");
		}

		if (contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Pessoa responsável deve ser informada!");
		}

		if (contaPagar.getPessoaFornecedor() == null || contaPagar.getPessoaFornecedor().getId() <= 0) {
			throw new ExceptionMentoriaJava("Fornecedor responsável deve ser informado!");
		}

		if (contaPagar.getId() == null) {
			List<ContaPagar> contaPagarList = contaPagarRepository.buscarContaPorDescricao(contaPagar.getDescricao().toUpperCase().trim());
			if (!contaPagarList.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe conta a pagar com a descrição informada! " + contaPagar.getDescricao());
			}
		}

		ContaPagar contaPagarSalva = contaPagarRepository.save(contaPagar);
		return new ResponseEntity<>(contaPagarSalva, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteContaPagar")
	public ResponseEntity<String> deleteContaPagar(@RequestBody ContaPagar contaPagar) {
		contaPagarRepository.deleteById(contaPagar.getId());
		return new ResponseEntity<>("Conta a pagar removida com sucesso!", HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteContaPagarPorId/{id}")
	public ResponseEntity<String> deleteContaPagarPorId(@PathVariable("id") Long id) {
		contaPagarRepository.deleteById(id);
		return new ResponseEntity<>("Conta a pagar removida com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterContaPagarPorId/{id}")
	public ResponseEntity<ContaPagar> pesquisarPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
		ContaPagar contaPagar = contaPagarRepository.findById(id).orElseThrow(() -> new ExceptionMentoriaJava("Conta a pagar com o id " + id + " não encontrado!"));
		return new ResponseEntity<>(contaPagar, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterContaPagarPorDescricao/{descricao}")
	public ResponseEntity<List<ContaPagar>> obterContaPagarPorDescricao(@PathVariable("descricao") String descricao) {
		List<ContaPagar> contaPagarList = contaPagarRepository.buscarContaPorDescricao(descricao.toUpperCase());
		return new ResponseEntity<>(contaPagarList, HttpStatus.OK);
	}

}
