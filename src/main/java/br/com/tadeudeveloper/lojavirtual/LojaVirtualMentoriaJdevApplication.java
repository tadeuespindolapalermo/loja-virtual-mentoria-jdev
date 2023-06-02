package br.com.tadeudeveloper.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EntityScan(basePackages = "br.com.tadeudeveloper.lojavirtual.model")
//@ComponentScan(basePackages = {"br.*"})
//@EnableJpaRepositories(basePackages = {"br.com.tadeudeveloper.lojavirtual.repository"})
//@EnableTransactionManagement
public class LojaVirtualMentoriaJdevApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoriaJdevApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
