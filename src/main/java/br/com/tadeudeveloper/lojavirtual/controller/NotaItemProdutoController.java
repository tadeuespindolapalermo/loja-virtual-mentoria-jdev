package br.com.tadeudeveloper.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tadeudeveloper.lojavirtual.ExceptionMentoriaJava;
import br.com.tadeudeveloper.lojavirtual.model.NotaItemProduto;
import br.com.tadeudeveloper.lojavirtual.repository.NotaItemProdutoRepository;

@RestController
public class NotaItemProdutoController {
	
	@Autowired
	private NotaItemProdutoRepository notaItemProdutoRepository;
	
	@PostMapping(value = "**/salvarNotaItemProduto")
	public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto) throws ExceptionMentoriaJava {
		if (notaItemProduto.getId() == null) {
			if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <=0) {
				throw new ExceptionMentoriaJava("Produto deve ser informado!");
			}
			if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <=0) {
				throw new ExceptionMentoriaJava("Nota Fiscal de Compra deve ser informado!");
			}
			if (notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <=0) {
				throw new ExceptionMentoriaJava("Empresa deve ser informado!");
			}
			List<NotaItemProduto> notasExistentes = notaItemProdutoRepository.buscarNotaItemPorProdutoNota(notaItemProduto.getProduto().getId(), notaItemProduto.getNotaFiscalCompra().getId());
			 if (!notasExistentes.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe este produto cadastrado para esta nota!");
			}
		}
		if (notaItemProduto.getQuantidade() <= 0) {
			throw new ExceptionMentoriaJava("A quantidade do produto deve ser informada!");
		}
		NotaItemProduto notaItemSalva = notaItemProdutoRepository.save(notaItemProduto);
		return new ResponseEntity<>(notaItemSalva, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteNotaItemProdutoPorId/{id}")
	public ResponseEntity<String> deleteNotaItemProdutoPorId(@PathVariable("id") Long id) {
		notaItemProdutoRepository.deleteByIdNotaItem(id);
		return new ResponseEntity<>("Nota Item Produto removido com sucesso!", HttpStatus.OK);
	}
}
