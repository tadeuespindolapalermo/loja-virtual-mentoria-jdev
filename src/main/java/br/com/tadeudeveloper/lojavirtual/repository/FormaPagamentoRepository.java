package br.com.tadeudeveloper.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tadeudeveloper.lojavirtual.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
}
