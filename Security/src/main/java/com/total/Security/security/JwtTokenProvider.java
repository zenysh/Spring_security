package com.total.Security.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.total.Security.model.Login;
import com.total.Security.model.User;
import com.total.Security.repository.LoginRespository;
import com.total.Security.repository.UserRepository;
import com.total.Security.utils.CONSTANTS;
import com.total.Security.utils.Status;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@SuppressWarnings("serial")
public class JwtTokenProvider implements Serializable {
	
	public String getUsernameFromToken(String token)
	{
	return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String Token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(Token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token)
	{
		return Jwts.parser().setSigningKey(CONSTANTS.SIGNING_KEY).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpiredOrNot(String token)
	{
	final Date expiration = getExpirationDateFromToken(token);
	return expiration.before(new Date());
	}
	
	public String GenerateToken(Login user)
	{
		return letsgenerateToken(user.getUsername());
	}
	
	@Autowired
	private LoginRespository loginrepo;
	
	@Autowired
	private UserRepository userrepo;
	
	private String letsgenerateToken(String username)
	{
		Login login = loginrepo.findByUsername(username);
		User user = userrepo.findByIdAndStatusNot(login.getUser().getId(), Status.DELETED);
		
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		
		return Jwts.builder().setClaims(claims).setIssuer("Jenish")
		.setId(login.getLogin_id().toString())
		.claim("user", user.getName())
		.claim("login", login.getUsername())
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + CONSTANTS.ACCESS_TOKEN_VALIDITY_SECONDS * 100))
		.signWith(SignatureAlgorithm.HS256, CONSTANTS.SIGNING_KEY).compact();
		
	}
	
	public Boolean ValidateToken(String token, UserDetails userDetails)
	{
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername())/* && !isTokenExpiredOrNot(token)*/);
	}
	
	UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {

		final JwtParser jwtParser = Jwts.parser().setSigningKey(CONSTANTS.SIGNING_KEY);

		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

		final Claims claims = claimsJws.getBody();

		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(CONSTANTS.AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(userDetails, " ", authorities);
	}

}
