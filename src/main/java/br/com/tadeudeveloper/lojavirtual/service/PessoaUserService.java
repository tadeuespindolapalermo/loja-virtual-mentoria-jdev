package br.com.tadeudeveloper.lojavirtual.service;

import br.com.tadeudeveloper.lojavirtual.model.PessoaJuridica;
import br.com.tadeudeveloper.lojavirtual.model.Usuario;
import br.com.tadeudeveloper.lojavirtual.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tadeudeveloper.lojavirtual.repository.UsuarioRepository;

import java.util.Calendar;

@Service
public class PessoaUserService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

		//pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}

		pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

		if (usuarioPj == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaJuridica);
			usuarioPj.setPessoa(pessoaJuridica);
			usuarioPj.setLogin(pessoaJuridica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);

			usuarioPj.setSenha(senhaCriptografada);
			usuarioPj = usuarioRepository.save(usuarioPj);

			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_USER");
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");

			// Envio e-mail login e senha
			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual:</b><br/><br/>");
			mensagemHtml.append("<b>Login:</b> " + pessoaJuridica.getEmail() + "<br/>");
			mensagemHtml.append("<b>Senha:</b> ").append(senha).append("<br/><br/>");
			mensagemHtml.append("<b>Muito obrigado!</b>");
			try {
				serviceSendEmail.enviarEmailHtml(
					"Acesso gerado para Loja Virtual",
					mensagemHtml.toString(),
					pessoaJuridica.getEmail()
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pessoaJuridica;
	}

}
