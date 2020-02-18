package com.andersonribeiro.minhasfinancas.api.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonribeiro.minhasfinancas.api.dto.LancamentoDTO;
import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Lancamento;
import com.andersonribeiro.minhasfinancas.model.entity.Usuario;
import com.andersonribeiro.minhasfinancas.model.enums.StatusLancamento;
import com.andersonribeiro.minhasfinancas.model.enums.TipoLancamento;
import com.andersonribeiro.minhasfinancas.service.LancamentoService;
import com.andersonribeiro.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {
	
	private LancamentoService service;
	private UsuarioService usuarioService;
	
	public LancamentoResource(LancamentoService service) {
		this.service = service;
	}
	
	private Lancamento converter(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		
		Usuario usuario = usuarioService
				.obterUsuarioPorId(dto.getUsuario())
				.orElseThrow( () -> new RegraNegocioException("Usuário não encontrado para o id informado"));
		
		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		
		return lancamento;
	}

}
