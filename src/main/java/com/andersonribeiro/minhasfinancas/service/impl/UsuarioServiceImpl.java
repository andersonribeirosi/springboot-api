package com.andersonribeiro.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andersonribeiro.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Usuario;
import com.andersonribeiro.minhasfinancas.model.repository.UsuarioRepository;
import com.andersonribeiro.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);

		if (!usuario.isPresent()) {
			throw new ErroAutenticacaoException("Usuario não encontrado para este email");
		}

		if (!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacaoException("Senha inválida para o email informado");
		}

		return usuario.get();
	}

	@Override
	@Transactional // Abre no BD uma transação, executa o método de salvar usuário e commita
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
		}
	}

	@Override
	public Optional<Usuario> obterUsuarioPorId(Long id) {
		return repository.findById(id);
	}

}
