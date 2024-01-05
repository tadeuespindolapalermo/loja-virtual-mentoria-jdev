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
import br.com.tadeudeveloper.lojavirtual.model.NotaFiscalCompra;
import br.com.tadeudeveloper.lojavirtual.repository.NotaFiscalCompraRepository;

@RestController
public class NotaFiscalCompraController {
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@PostMapping(value = "**/salvarNotaFiscalCompra")
	public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws ExceptionMentoriaJava {
		if (notaFiscalCompra.getId() == null && notaFiscalCompra.getDescricaoObservacao() != null) {
			// List<NotaFiscalCompra> notasFiscaisCompra = notaFiscalCompraRepository.buscarNotaPorDescricao(notaFiscalCompra.getDescricaoObservacao().toUpperCase().trim());
			boolean existe = notaFiscalCompraRepository.existeNotaComDescricao(notaFiscalCompra.getDescricaoObservacao().toUpperCase().trim());
			if (existe) {
			// if (!notasFiscaisCompra.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe nota fiscal de compra com a descrição: " + notaFiscalCompra.getDescricaoObservacao());
			}
		}
		if (notaFiscalCompra.getPessoa() == null || notaFiscalCompra.getPessoa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A pessoa jurídica da nota fiscal de compra deve ser informada!");
		}
		if (notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A empresa responsável da nota fiscal de compra deve ser informada!");
		}
		if (notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <= 0) {
			throw new ExceptionMentoriaJava("A conta a pagar da nota fiscal de compra deve ser informada!");
		}
		NotaFiscalCompra notasFiscaisCompraSalva = notaFiscalCompraRepository.save(notaFiscalCompra);
		return new ResponseEntity<>(notasFiscaisCompraSalva, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteNotaFiscalCompraPorId/{id}")
	public ResponseEntity<String> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) {
		notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id); // Deleta os filhos
		notaFiscalCompraRepository.deleteById(id); // Deleta o pai
		return new ResponseEntity<>("Nota Fiscal de Compra removida com sucesso!", HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterNotaFiscalCompraPorId/{id}")
	public ResponseEntity<NotaFiscalCompra> pesquisarNotaFiscalCompraPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
		NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElseThrow(() -> new ExceptionMentoriaJava("Nota Fiscal de Compra com o id " + id + " não encontrado!"));
		return new ResponseEntity<>(notaFiscalCompra, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterNotaFiscalCompraPorDescricao/{desc}")
	public ResponseEntity<List<NotaFiscalCompra>> pesquisarPorNomeDescricao(@PathVariable("desc") String desc) {
		List<NotaFiscalCompra> notasFiscaisCompra = notaFiscalCompraRepository.buscarNotaPorDescricao(desc.toUpperCase().trim());
		return new ResponseEntity<>(notasFiscaisCompra, HttpStatus.OK);
	}

}
