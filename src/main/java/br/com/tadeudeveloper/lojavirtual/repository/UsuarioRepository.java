package br.com.tadeudeveloper.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.tadeudeveloper.lojavirtual.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("SELECT u FROM Usuario u WHERE u.login = ?1")
	Usuario findUserByLogin(String login);
}
