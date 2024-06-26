package com.ecritic.ecritic_users_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ECriticUsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECriticUsersServiceApplication.class, args);
	}
}
