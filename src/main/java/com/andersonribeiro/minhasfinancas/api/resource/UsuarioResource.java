package com.andersonribeiro.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonribeiro.minhasfinancas.api.dto.UsuarioDTO;
import com.andersonribeiro.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Usuario;
import com.andersonribeiro.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {
		
	private final UsuarioService service;
	
	//@ResposeEntity - Representa o corpo da resposta
	//@RequestBody - Retorna apenas o que contem no DTO
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
	try {
		Usuario usuarioSalvo = service.salvarUsuario(usuario);
		return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		
	} catch (RegraNegocioException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}	
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
					
		return ResponseEntity.ok(usuarioAutenticado);
		
		} catch (ErroAutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
