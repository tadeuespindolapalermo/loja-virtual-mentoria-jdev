package br.com.tadeudeveloper.lojavirtual.service;

import br.com.tadeudeveloper.lojavirtual.model.Usuario;
import br.com.tadeudeveloper.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class TarefaAutomatizadaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    //@Scheduled(initialDelay = 2000, fixedDelay = 86400000) // roda a cada 24h (já roda quando sobe a aplicação)
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") // roda todos os dias às 11h
    public void notificarUsuarioTrocaSenha() throws MessagingException, UnsupportedEncodingException, InterruptedException {

        List<Usuario> usuarios = usuarioRepository.obterUsuariosComSenhaVencida();

        for (Usuario usuario : usuarios) {
            StringBuilder msg = new StringBuilder();
            msg
                .append("Olá, ")
                .append(usuario.getPessoa().getNome())
                .append("<br/>")
                .append("Está na hora de trocar a sua senha! Já passou 90 dias de validade.")
                .append("<br/>")
                .append("Troque sua senha da loja virtual!!!");

            serviceSendEmail.enviarEmailHtml(
                "Troca de senha!", msg.toString(), usuario.getLogin()
            );

            Thread.sleep(3000);
        }

    }
}
