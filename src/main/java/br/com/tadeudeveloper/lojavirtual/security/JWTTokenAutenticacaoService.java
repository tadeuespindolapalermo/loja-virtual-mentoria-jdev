package br.com.tadeudeveloper.lojavirtual.security;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.tadeudeveloper.lojavirtual.ApplicationContextLoad;
import br.com.tadeudeveloper.lojavirtual.model.Usuario;
import br.com.tadeudeveloper.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAutenticacaoService {
	
	// 11 dias
	private static final long EXPIRATION_TIME = 959990000;
	
	private static final String SECRET = "qualquer-senha-secreta";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		// Montagem do Token
		String jwt = 
				Jwts.builder()
					.setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS512, SECRET)
					.compact();
		
		String token = TOKEN_PREFIX + " " + jwt;
		
		response.addHeader(HEADER_STRING, token);
		
		liberarCorsPolicy(response);
		
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}
	
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(HEADER_STRING);
		
		if (nonNull(token)) {
			String tokenLimpo = token.replace(TOKEN_PREFIX, "");
			String user = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(tokenLimpo)
				.getBody()
				.getSubject();
			
			if (nonNull(user)){
				Usuario usuario = ApplicationContextLoad.getApplicationContext()
					.getBean(UsuarioRepository.class)
					.findUserByLogin(user);
				
				if (nonNull(usuario)) {
					return new UsernamePasswordAuthenticationToken(
						usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities()
					);
				}
			}
		}
		
		liberarCorsPolicy(response);
		
		return null;
	}
	
	// CORS policy
	private void liberarCorsPolicy(HttpServletResponse response) {
		if (isNull(response.getHeader("Access-Control-Allow-Origin"))) {
			response.addHeader("Access-Control-Allow-Origin", "*");		
		}
		if (isNull(response.getHeader("Access-Control-Allow-Headers"))) {
			response.addHeader("Access-Control-Allow-Headers", "*");		
		}
		if (isNull(response.getHeader("Access-Control-Request-Headers"))) {
			response.addHeader("Access-Control-Request-Headers", "*");		
		}
		if (isNull(response.getHeader("Access-Control-Allow-Methods"))) {
			response.addHeader("Access-Control-Allow-Methods", "*");		
		}
	}

}
