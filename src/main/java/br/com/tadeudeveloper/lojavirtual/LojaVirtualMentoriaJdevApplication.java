package br.com.tadeudeveloper.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan(basePackages = "br.com.tadeudeveloper.lojavirtual.model")
//@ComponentScan(basePackages = {"br.*"})
//@EnableJpaRepositories(basePackages = {"br.com.tadeudeveloper.lojavirtual.repository"})
//@EnableTransactionManagement
public class LojaVirtualMentoriaJdevApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoriaJdevApplication.class, args);
	}

}
