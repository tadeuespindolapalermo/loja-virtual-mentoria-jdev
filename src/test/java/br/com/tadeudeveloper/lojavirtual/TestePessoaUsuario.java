package br.com.tadeudeveloper.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.tadeudeveloper.lojavirtual.model.PessoaFisica;
import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaJuridicaRepository;
import br.com.tadeudeveloper.lojavirtual.service.PessoaUserService;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaJdevApplication.class)
class TestePessoaUsuario extends TestCase {
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Test
	void testCadPessoa() {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("98765432100765");
		pessoaJuridica.setNome("Tadeu");
		pessoaJuridica.setEmail("tadeupalermoti@gmail.com");
		pessoaJuridica.setTelefone("61987653486");
		pessoaJuridica.setInscricaoEstadual("34535354345");
		pessoaJuridica.setInscricaoMunicipal("656655656565656");
		pessoaJuridica.setNomeFantasia("Taaaddeeuu");
		pessoaJuridica.setRazaoSocial("Taaaddeeuu");
		pessoaJuridicaRepository.save(pessoaJuridica);
		
//		PessoaFisica pessoaFisica = new PessoaFisica();
//		pessoaFisica.setCpf("98765432100");
//		pessoaFisica.setNome("Tadeu");
//		pessoaFisica.setEmail("tadeupalermoti@gmail.com");
//		pessoaFisica.setTelefone("61987653486");
//		pessoaFisica.setEmpresa(pessoaFisica);
		
	}

}
