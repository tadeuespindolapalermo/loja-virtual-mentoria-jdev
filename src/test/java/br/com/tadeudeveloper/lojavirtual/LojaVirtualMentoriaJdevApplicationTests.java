package br.com.tadeudeveloper.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tadeudeveloper.lojavirtual.controller.AcessoController;
import br.com.tadeudeveloper.lojavirtual.model.Acesso;
import br.com.tadeudeveloper.lojavirtual.repository.AcessoRepository;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualMentoriaJdevApplication.class)
class LojaVirtualMentoriaJdevApplicationTests extends TestCase {

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private AcessoController acessoController;

	@Autowired
	private WebApplicationContext wac;

	@Test
	void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
			MockMvcRequestBuilders
				.post("/salvarAcesso")
				.content(mapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);		
		
		Acesso objetoRetorno = mapper.readValue(
			retornoApi.andReturn().getResponse().getContentAsString(), 
			Acesso.class
		);
		
		assertEquals(objetoRetorno.getDescricao(), acesso.getDescricao());
		assertNotNull(objetoRetorno.getId());
	}
	
	@Test
	void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TEST_DELETE");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
			MockMvcRequestBuilders
				.post("/deleteAcesso")
				.content(mapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);		
		
		assertEquals("Acesso removido com sucesso!", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	@Test
	void testRestApiDeleteAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TEST_DELETE");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
			MockMvcRequestBuilders
				.delete("/deleteAcessoPorId/" + acesso.getId())
				.content(mapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);		
		
		assertEquals("Acesso removido com sucesso!", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	@Test
	void testRestApiObterAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_OBTER_POR_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/obterAcessoPorId/" + acesso.getId())
				.content(mapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);		
		
		Acesso acessoRetorno = mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		assertEquals(acesso.getId(), acessoRetorno.getId());
	}
	
	@Test
	void testRestApiObterAcessoPorDescricao() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_OBTER_POR_DESC");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/obterAcessoPorDescricao/OBTER_POR_DESC")
				.content(mapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);		
		
		List<Acesso> retornoListaApi = mapper.readValue(
			retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<>(){}
		);
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		assertEquals(1, retornoListaApi.size());
		assertEquals(acesso.getDescricao(), retornoListaApi.get(0).getDescricao());
		
		acessoRepository.deleteById(acesso.getId());
	}


	@Test
	void testCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");

		assertEquals(true, acesso.getId() == null);

		acesso = acessoController.salvarAcesso(acesso).getBody();

		assertEquals(true, acesso.getId() > 0);

		assertEquals("ROLE_ADMIN", acesso.getDescricao());

		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		assertEquals(acesso.getId(), acesso2.getId());

		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush();

		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		assertNull(acesso3);

		acesso = new Acesso();
		acesso.setDescricao("ROLE_ALUNO");
		acesso = acessoController.salvarAcesso(acesso).getBody();

		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

		assertEquals(1, acessos.size());

		acessoRepository.deleteById(acesso.getId());

	}

}
