package br.com.tadeudeveloper.lojavirtual;

import br.com.tadeudeveloper.lojavirtual.controller.PessoaController;
import br.com.tadeudeveloper.lojavirtual.enums.TipoEndereco;
import br.com.tadeudeveloper.lojavirtual.model.Endereco;
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

		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("65656656");
		endereco1.setComplemento("Casa zcinaza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogra("Av. São João sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");

		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Marana");
		endereco2.setCep("76767676");
		endereco2.setComplemento("Andar 4");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogra("Av. Maaringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		endereco2.setCidade("Maringá");

		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);

		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();

		assertEquals(true, pessoaJuridica.getId() > 0);

		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}

		assertEquals(2, pessoaJuridica.getEnderecos().size());
		
	}

}
