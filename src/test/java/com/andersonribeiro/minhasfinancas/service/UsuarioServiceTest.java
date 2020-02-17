package com.andersonribeiro.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.andersonribeiro.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Usuario;
import com.andersonribeiro.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

	@SpyBean
	UsuarioService service;

	@MockBean
	UsuarioRepository repository;

//	@Before
//	public void setUp() {
//		repository = Mockito.mock(UsuarioRepository.class); 
//		service = new UsuarioServiceImpl(repository);
//	}

	@Test(expected = Test.None.class)
	public void deveSalvarUmUsuario() {
		// Cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).email("email@email.com").nome("nome").senha("senha").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

		// Ação
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

		// Verificação
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
	}

	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		// Cenário
		String email = "anderson@email.com";
		String senha = "senha";

		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		// Ação
		Usuario result = service.autenticar(email, senha);

		// Verificação
		Assertions.assertThat(result).isNotNull();

	}

	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioParaOemailInformado() {
		// Cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// Ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("anderson@gmail.com", "123456"));

		// Verificação
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacaoException.class)
				.hasMessage("Usuario não encontrado para este email");
	}

	@Test
	public void deveLancarErroQuandoAsenhaNaoBater() {
		// Cenário
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		// Ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "1904"));

		// Verificação
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacaoException.class)
				.hasMessage("Senha inválida para o email informado");
	}

	@Test(expected = Test.None.class) // Quando um método retorna uma exception, com essa anotação expected =
										// Test.None.class, não sera retornada nenhuma exception
	public void deveValidarEmail() {

		// Cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// Ação // Execução
		service.validarEmail("anderson@gmail.com");

	}

	@Test(expected = RegraNegocioException.class) // Aqui o teste espera que seja lançanda a exception
													// RegraNegocioException
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// Cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

		// Ação
		service.validarEmail("andersonribeiro.sifacisa@gmail.com");
	}

}
/**
 * @Autowired UsuarioService service;
 * 
 * @Autowired UsuarioRepository repository;
 * 
 * @Test(expected = Test.None.class) // Quando um método retorna uma exception,
 *                com essa anotação expected = Test.None.class, não sera
 *                retornada nenhuma exception public void deveValidarEmail() {
 * 
 *                //Cenário repository.deleteAll();
 * 
 *                //Ação // Execução service.validarEmail("anderson@gmail.com");
 * 
 *                }
 * 
 * @Test(expected = RegraNegocioException.class) // Aqui o teste espera que seja
 *                lançanda a exception RegraNegocioException public void
 *                deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
 *                //Cenário Usuario usuario =
 *                Usuario.builder().nome("usuario").email("andersonribeiro.sifacisa@gmail.com").build();
 *                repository.save(usuario);
 * 
 *                //Ação
 *                service.validarEmail("andersonribeiro.sifacisa@gmail.com"); }
 *                }
 **/
