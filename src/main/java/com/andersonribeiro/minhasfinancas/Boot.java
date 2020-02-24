package com.andersonribeiro.minhasfinancas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.andersonribeiro.minhasfinancas.api.controllers.LancamentoController;

@SpringBootApplication
public class Boot {
	
	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

}
