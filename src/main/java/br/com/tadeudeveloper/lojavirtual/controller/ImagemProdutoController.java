package br.com.tadeudeveloper.lojavirtual.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tadeudeveloper.lojavirtual.model.ImagemProduto;
import br.com.tadeudeveloper.lojavirtual.model.dto.ImagemProdutoDTO;
import br.com.tadeudeveloper.lojavirtual.repository.ImagemProdutoRepository;

@RestController
public class ImagemProdutoController {
	
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	@PostMapping(value = "**/salvarImagemProduto")
	public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) {
		imagemProduto = imagemProdutoRepository.saveAndFlush(imagemProduto);
		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
		imagemProdutoDTO.setId(imagemProduto.getId());
		imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
		imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
		imagemProdutoDTO.setImagemMiniatura(imagemProduto.getImagemMiniatura());
		imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
		return new ResponseEntity<>(imagemProdutoDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteTodasImagensProduto/{idProduto}")
	public ResponseEntity<String> deleteTodasImagensProduto(@PathVariable("idProduto") Long idProduto) {
		imagemProdutoRepository.deletarTodasImagensDoProduto(idProduto);
		return new ResponseEntity<>("Imagens do produto removidas!", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteImagemObjeto")
	public ResponseEntity<String> deleteImagemProdutoPorId(@RequestBody ImagemProduto imagemProduto) {
		if (!imagemProdutoRepository.existsById(imagemProduto.getId())) {
			return new ResponseEntity<>("Imagem já foi removida ou no existe com esse id: " + imagemProduto.getId(), HttpStatus.OK);
		}
		imagemProdutoRepository.deleteById(imagemProduto.getId());
		return new ResponseEntity<>("Imagem removida!", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteImagemProdutoPorId/{idImagem}")
	public ResponseEntity<String> deleteImagemProdutoPorId(@PathVariable("idImagem") Long idImagem) {
		if (!imagemProdutoRepository.existsById(idImagem)) {
			return new ResponseEntity<>("Imagem já foi removida ou no existe com esse id: " + idImagem, HttpStatus.OK);
		}
		imagemProdutoRepository.deleteById(idImagem);
		return new ResponseEntity<>("Imagem removida!", HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterImagemPorProduto/{idProduto}")
	public ResponseEntity<List<ImagemProdutoDTO>> obterImagemPorProduto(@PathVariable("idProduto") Long idProduto) {
		List<ImagemProdutoDTO> dtos = new ArrayList<>();
		
		List<ImagemProduto> imagensProduto = imagemProdutoRepository.buscarImagensProduto(idProduto);
		
		for (ImagemProduto imagemProduto : imagensProduto) {
			ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
			imagemProdutoDTO.setId(imagemProduto.getId());
			imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
			imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
			imagemProdutoDTO.setImagemMiniatura(imagemProduto.getImagemMiniatura());
			imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
			dtos.add(imagemProdutoDTO);
		}
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}
