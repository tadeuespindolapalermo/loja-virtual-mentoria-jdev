package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.Produto;
import br.com.tadeudeveloper.lojavirtual.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@PostMapping(value = "**/salvarProduto")
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava {
		if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Empresa responsável deve ser informada!");
		}

		if (produto.getId() == null) {
			List<Produto> produtos = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase(), produto.getEmpresa().getId());
			if (!produtos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe produto com o nome: " + produto.getNome());
			}
		}

		if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("Categoria do produto deve ser informada!");
		}

		if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("Marca do produto deve ser informada!");
		}
		
		Produto produtoSalvo = produtoRepository.save(produto);

		return new ResponseEntity<>(produtoSalvo, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteProduto")
	public ResponseEntity<String> deleteProduto(@RequestBody Produto produto) {
		produtoRepository.deleteById(produto.getId());
		return new ResponseEntity<>("Produto removido com sucesso!", HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteProdutoPorId/{id}")
	public ResponseEntity<String> deleteProdutoPorId(@PathVariable("id") Long id) {
		produtoRepository.deleteById(id);
		return new ResponseEntity<>("Produto removido com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterProdutoPorId/{id}")
	public ResponseEntity<Produto> pesquisarPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ExceptionMentoriaJava("Produto com o id " + id + " não encontrado!"));
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterProdutoPorNome/{nome}")
	public ResponseEntity<List<Produto>> obterProdutoPorNome(@PathVariable("nome") String nome) {
		List<Produto> produtos = produtoRepository.buscarProdutoNome(nome.toUpperCase());
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}

}
