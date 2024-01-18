package br.com.tadeudeveloper.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.AvaliacaoProduto;
import br.com.tadeudeveloper.lojavirtual.repository.AvaliacaoProdutoRepository;

@RestController
public class AvaliacaoProdutoController {
	
	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	
	@PostMapping(value = "**/salvarAvaliacaoProduto")
	public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoriaJava {
		if (avaliacaoProduto.getEmpresa() == null || (avaliacaoProduto.getEmpresa() == null && avaliacaoProduto.getEmpresa().getId() <= 0)) {
			throw new ExceptionMentoriaJava("Informe a empresa dona do registro!");
		}
		if (avaliacaoProduto.getProduto() == null || (avaliacaoProduto.getProduto() == null && avaliacaoProduto.getProduto().getId() <= 0)) {
			throw new ExceptionMentoriaJava("A avaliação deve conter o produto associado.");
		}
		if (avaliacaoProduto.getPessoa() == null || (avaliacaoProduto.getPessoa() == null && avaliacaoProduto.getPessoa().getId() <= 0)) {
			throw new ExceptionMentoriaJava("A avaliação deve conter a pessoa ou cliente associado.");
		}
		avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
		return new ResponseEntity<>(avaliacaoProduto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteAvaliacaoPessoa/{idAvaliacao}")
	public ResponseEntity<String> deleteAvaliacaoPessoa(@PathVariable("idAvaliacao") Long idAvaliacao) {
		avaliacaoProdutoRepository.deleteById(idAvaliacao);
		return new ResponseEntity<>("Avaliação removida com sucesso!", HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterAvaliacaoProduto/{idProduto}")
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoProduto(@PathVariable("idProduto") Long idProduto) {
		List<AvaliacaoProduto> avaliacoesProduto = avaliacaoProdutoRepository.avaliacoesProduto(idProduto);
		return new ResponseEntity<>(avaliacoesProduto, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterAvaliacaoProdutoPessoa/{idProduto}/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoProdutoPessoa(
		@PathVariable("idProduto") Long idProduto, 
		@PathVariable("idPessoa") Long idPessoa
	) {
		List<AvaliacaoProduto> avaliacoesProdutoPessoa = avaliacaoProdutoRepository.avaliacoesProdutoPessoa(idProduto, idPessoa);
		return new ResponseEntity<>(avaliacoesProdutoPessoa, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterAvaliacaoPessoa/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoPessoa(@PathVariable("idPessoa") Long idPessoa) {
		List<AvaliacaoProduto> avaliacoesPessoa = avaliacaoProdutoRepository.avaliacoesPessoa(idPessoa);
		return new ResponseEntity<>(avaliacoesPessoa, HttpStatus.OK);
	}

}
