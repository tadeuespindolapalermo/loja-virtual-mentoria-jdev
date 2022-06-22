package br.com.tadeudeveloper.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tadeudeveloper.lojavirtual.controller.AcessoController;
import br.com.tadeudeveloper.lojavirtual.model.Acesso;
import br.com.tadeudeveloper.lojavirtual.repository.AcessoRepository;
import br.com.tadeudeveloper.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualMentoriaJdevApplication.class)
class LojaVirtualMentoriaJdevApplicationTests {

	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoController acessoController;

	@Test
	void testCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoController.salvarAcesso(acesso);
	}

}
