package com.andersonribeiro.minhasfinancas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Usuario;
import com.andersonribeiro.minhasfinancas.model.repository.UsuarioRepository;
import com.andersonribeiro.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceTest {
	
	UsuarioService service;
	UsuarioRepository repository;
	
	@Before
	public void setUp() {
		repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test(expected = Test.None.class) // Quando um método retorna uma exception, com essa anotação expected = Test.None.class, não sera retornada nenhuma exception
	public void deveValidarEmail() {

		//Cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//Ação // Execução
		service.validarEmail("anderson@gmail.com");
			
	}
	
	@Test(expected = RegraNegocioException.class) // Aqui o teste espera que seja lançanda a exception RegraNegocioException
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//Cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//Ação
		service.validarEmail("andersonribeiro.sifacisa@gmail.com");
	}
	
}
	/**
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test(expected = Test.None.class) // Quando um método retorna uma exception, com essa anotação expected = Test.None.class, não sera retornada nenhuma exception
	public void deveValidarEmail() {
		
		//Cenário
		repository.deleteAll();
		
		//Ação // Execução
		service.validarEmail("anderson@gmail.com");
			
	}
	
	@Test(expected = RegraNegocioException.class) // Aqui o teste espera que seja lançanda a exception RegraNegocioException
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//Cenário
		Usuario usuario = Usuario.builder().nome("usuario").email("andersonribeiro.sifacisa@gmail.com").build();
		repository.save(usuario);
		
		//Ação
		service.validarEmail("andersonribeiro.sifacisa@gmail.com");
	}
}
**/
