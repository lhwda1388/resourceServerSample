package com.sample.rsserver.config;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.jwt.signKey}")
	private String signKey;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
			.antMatchers("/v1/users")
			.access("#oauth2.hasScope('read')").anyRequest()
			.authenticated();
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	/*
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    converter.setSigningKey(signKey);
	    return converter;
	}
	*/
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter convter = new JwtAccessTokenConverter();
		Resource resource = new ClassPathResource("pub.txt");
		String pubKey = null;
		try {
			BufferedInputStream bis =  new BufferedInputStream(resource.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result;
			while((result = bis.read()) != -1) {
				buf.write((byte) result);
			}
			pubKey = buf.toString(Charset.defaultCharset());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		convter.setVerifierKey(pubKey);
		
		return convter;
	}
}
