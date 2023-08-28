package br.com.tadeudeveloper.lojavirtual.controller;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.CategoriaProduto;
import br.com.tadeudeveloper.lojavirtual.model.dto.CategoriaProdutoDTO;
import br.com.tadeudeveloper.lojavirtual.repository.CategoriaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;

	@PostMapping("**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {

		if (categoriaProduto.getEmpresa() == null || categoriaProduto.getEmpresa().getId() == null) {
			throw new ExceptionMentoriaJava("A empre seve ser informada!");
		}

		if (categoriaProduto.getId() == null
				&& categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
			throw new ExceptionMentoriaJava("Categoria já existe com o nome informado!");
		}

		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);

		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		categoriaProdutoDTO.setId(categoriaSalva.getId());
		categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());

		return new ResponseEntity<>(categoriaProdutoDTO, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteCategoria")
	public ResponseEntity<String> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) {
		if (!categoriaProdutoRepository.findById(categoriaProduto.getId()).isPresent()) {
			return new ResponseEntity<>("Categoria já foi removida!", HttpStatus.OK);
		}

		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		return new ResponseEntity<>("Categoria removida com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterCategoriaPorDescricao/{desc}")
	public ResponseEntity<List<CategoriaProduto>> pesquisarPorDescricao(@PathVariable("desc") String desc) {
		List<CategoriaProduto> categorias = categoriaProdutoRepository.buscarCategoriaDesc(desc.toUpperCase());
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

}
