package com.sample.rsserver;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sample.rsserver.entity.User;
import com.sample.rsserver.repo.UserJpaRepo;

@Component
public class AppRunner implements ApplicationRunner {
	
	@Autowired
	private UserJpaRepo userJpaRepo;
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		userJpaRepo.save(
				User.builder()
				.uid("lhwda1388@gmail.com")
				.password("1234")
				.name("이현우")
				.roles(Collections.singletonList("ROLE_USER"))
				.build());
		
		
	}
}
