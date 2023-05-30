package br.com.tadeudeveloper.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAutenticacaoService {
	
	// 11 dias
	private static final long EXPIRATION_TIME = 959990000;
	
	private static final String SECRET = "qualquer-senha-secreta";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		// Montagem do Token
		String jwt = 
				Jwts.builder()
					.setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS512, SECRET)
					.compact();
		
		String token = TOKEN_PREFIX + " " + jwt;
		
		response.addHeader(HEADER_STRING, token);
		
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

}
