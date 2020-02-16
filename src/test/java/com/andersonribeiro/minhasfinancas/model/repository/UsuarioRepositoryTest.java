package com.andersonribeiro.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.andersonribeiro.minhasfinancas.model.entity.Usuario;

//@SpringBootTest
@RunWith(SpringRunner.class)
//@ActiveProfiles("test") // Vai buscar o application-test-properties (h2 in memory)
@DataJpaTest
// Cria uma instância do BD em mémoria, ao finalizar a bateria de testes, a encerra e deleta da memória
// Assim não influenciando nos proximos testes a cada ciclo.
@AutoConfigureTestDatabase(replace = Replace.NONE)
// Não altera as configurações no BD em memória (mantém as configs default pré-configuradas
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void verificarEmail() {

		// Cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// açao / execução
		boolean result = repository.existsByEmail("and@email.com");

		// verificação
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {

		// ação / execução
		boolean result = repository.existsByEmail("anderson@gmail.com");

		// Verficação
		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {

		// Cenário
		Usuario usuario = criarUsuario();
		// Ação
		Usuario usuarioSalvo = repository.save(usuario);
		
		// Verificação
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
		
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		//Cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		// Verificação
		Optional<Usuario> result = repository.findByEmail("and@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void deveRetornarVazioSeOemailNaoExistirNaBase() {
		
		Optional<Usuario> result = repository.findByEmail("anderson@gmail.com");
		
		Assertions.assertThat(result.isPresent()).isFalse();
		
	}
	
	public static Usuario criarUsuario() {
		return Usuario
				.builder()
				.nome("usuario")
				.email("and@email.com")
				.senha("senha")
				.build();
	}
	
	

	
}
