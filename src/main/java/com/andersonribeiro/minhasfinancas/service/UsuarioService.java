package com.andersonribeiro.minhasfinancas.service;

import com.andersonribeiro.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String usuario, String email);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);

}
