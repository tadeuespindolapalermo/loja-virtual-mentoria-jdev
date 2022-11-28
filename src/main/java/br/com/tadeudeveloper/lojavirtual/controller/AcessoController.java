package br.com.tadeudeveloper.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tadeudeveloper.lojavirtual.model.Acesso;
import br.com.tadeudeveloper.lojavirtual.repository.AcessoRepository;
import br.com.tadeudeveloper.lojavirtual.service.AcessoService;

@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;

	@PostMapping(value = "**/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {
		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<>(acessoSalvo, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteAcesso")
	public ResponseEntity<String> deleteAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity<>("Acesso removido com sucesso!", HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteAcessoPorId/{id}")
	public ResponseEntity<String> deleteAcessoPorId(@PathVariable("id") Long id) {
		acessoRepository.deleteById(id);
		return new ResponseEntity<>("Acesso removido com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterAcessoPorId/{id}")
	public ResponseEntity<Acesso> pesquisarPorId(@PathVariable("id") Long id) {
		Acesso acesso = acessoRepository.findById(id).orElse(new Acesso());
		return new ResponseEntity<>(acesso, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterAcessoPorDescricao/{desc}")
	public ResponseEntity<List<Acesso>> pesquisarPorDescricao(@PathVariable("desc") String desc) {
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc);
		return new ResponseEntity<>(acessos, HttpStatus.OK);
	}

}
