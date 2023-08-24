package br.com.tadeudeveloper.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.Executor;


@SpringBootApplication
@EnableAsync
//@EntityScan(basePackages = "br.com.tadeudeveloper.lojavirtual.model")
//@ComponentScan(basePackages = {"br.*"})
//@EnableJpaRepositories(basePackages = {"br.com.tadeudeveloper.lojavirtual.repository"})
//@EnableTransactionManagement
public class LojaVirtualMentoriaJdevApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualMentoriaJdevApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

	@Bean
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assyncrono Thread");
		executor.initialize();
		return executor;
	}

}
