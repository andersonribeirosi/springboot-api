package com.andersonribeiro.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.andersonribeiro.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
//@ActiveProfiles("test") // Vai buscar o application-test-properties (h2 in memory)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void verificarEmail() {
		
		// Cenário
		Usuario usuario = Usuario.builder().nome("usuario").email("and@email.com").build();
		repository.save(usuario);
	
		// açao / execução
		boolean result = repository.existsByEmail("and@email.com");
	
		// verificação
		Assertions.assertThat(result).isTrue();
	}

}
