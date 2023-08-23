package br.com.tadeudeveloper.lojavirtual;

import br.com.tadeudeveloper.lojavirtual.controller.PessoaController;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Calendar;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaJdevApplication.class)
class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaController pessoaController;
	
	@Test
	void testCadPessoa() throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Maria");
		pessoaJuridica.setEmail("maria@gmail.com");
		pessoaJuridica.setTelefone("9999999999");
		pessoaJuridica.setInscricaoEstadual("9999999999");
		pessoaJuridica.setInscricaoMunicipal("9999999999");
		pessoaJuridica.setNomeFantasia("9999999999");
		pessoaJuridica.setRazaoSocial("9999999999");

		pessoaController.salvarPj(pessoaJuridica);
		
	}

}
