package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.MarcaProduto;
import br.com.tadeudeveloper.lojavirtual.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MarcaProdutoController {

	@Autowired
	private MarcaRepository marcaRepository;

	@PostMapping(value = "**/salvarMarca")
	public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava {
		if (marcaProduto.getId() == null) {
			List<MarcaProduto> marcas = marcaRepository.buscarMarcaProdutoPorNomeDescricao(marcaProduto.getNomeDesc().toUpperCase());
			if (!marcas.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe marca do produto com a descrição: " + marcaProduto.getNomeDesc());
			}
		}
		MarcaProduto marcaSalva = marcaRepository.save(marcaProduto);
		return new ResponseEntity<>(marcaSalva, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteMarca")
	public ResponseEntity<String> deleteMarca(@RequestBody MarcaProduto marcaProduto) {
		marcaRepository.deleteById(marcaProduto.getId());
		return new ResponseEntity<>("Marca do produto removida com sucesso!", HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteMarcaPorId/{id}")
	public ResponseEntity<String> deleteMarcaPorId(@PathVariable("id") Long id) {
		marcaRepository.deleteById(id);
		return new ResponseEntity<>("Marca do produto removida com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterMarcaProdutoPorId/{id}")
	public ResponseEntity<MarcaProduto> pesquisarMarcaPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
		MarcaProduto marcaProduto = marcaRepository.findById(id).orElseThrow(() -> new ExceptionMentoriaJava("Marca do produto com o id " + id + " não encontrado!"));
		return new ResponseEntity<>(marcaProduto, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterMarcaProdutoPorNomeDescricao/{nomeDesc}")
	public ResponseEntity<List<MarcaProduto>> pesquisarPorNomeDescricao(@PathVariable("nomeDesc") String nomeDesc) {
		List<MarcaProduto> marcas = marcaRepository.buscarMarcaProdutoPorNomeDescricao(nomeDesc.toUpperCase().trim());
		return new ResponseEntity<>(marcas, HttpStatus.OK);
	}

}
