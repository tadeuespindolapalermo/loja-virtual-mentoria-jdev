package br.com.tadeudeveloper.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tadeudeveloper.lojavirtual.model.VendaCompraLojaVirtual;

@Transactional
public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {


	
}
