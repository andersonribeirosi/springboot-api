package com.andersonribeiro.minhasfinancas.api.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andersonribeiro.minhasfinancas.api.dto.AtualizaStatusDTO;
import com.andersonribeiro.minhasfinancas.api.dto.LancamentoDTO;
import com.andersonribeiro.minhasfinancas.api.dto.ProdutoDTO;
import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Lancamento;
import com.andersonribeiro.minhasfinancas.model.entity.Produto;
import com.andersonribeiro.minhasfinancas.model.entity.Usuario;
import com.andersonribeiro.minhasfinancas.model.enums.StatusLancamento;
import com.andersonribeiro.minhasfinancas.model.enums.TipoLancamento;
import com.andersonribeiro.minhasfinancas.service.LancamentoService;
import com.andersonribeiro.minhasfinancas.service.ProdutoService;
import com.andersonribeiro.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService service;
	private final UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity salvar(@RequestBody ProdutoDTO dto) {
		try {
			Produto entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity obterProduto(@PathVariable("id") Long id) {
		return service.obterPorId(id)
				.map(produto -> new ResponseEntity(converter(produto), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Produto produto = converter(dto);
				produto.setId(entity.getId());
				service.atualizar(produto);
				return ResponseEntity.ok(produto);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "usuario") Long idUsuario) {
		Produto produtoFiltro = new Produto();
		produtoFiltro.setDescricao(descricao);
		
		Optional<Usuario> usuario = usuarioService.obterUsuarioPorId(idUsuario);
		if (!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta");
		} else {
			produtoFiltro.setUsuario(usuario.get());
		}

		List<Produto> produtos = service.buscar(produtoFiltro);
		return ResponseEntity.ok(produtos);

	}

//	@PutMapping("{id}/atualiza-status")
//	private ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
//		return service.obterPorId(id).map(entity -> {
//			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
//
//			if (statusSelecionado == null) {
//				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento");
//			}
//
//			try {
//				entity.setStatus(statusSelecionado);
//				service.atualizar(entity);
//				return ResponseEntity.ok(entity);
//			} catch (RegraNegocioException e) {
//				return ResponseEntity.badRequest().body(e.getMessage());
//			}
//		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
//	}
	
	
	private ProdutoDTO converter(Produto produto) {
		return ProdutoDTO.builder()
				.id(produto.getId())
				.descricao(produto.getDescricao())
				.quantidade(produto.getQuantidade())
				.dataCadastro(produto.getDataCadastro())
				.valor(produto.getValor())
				.usuario(produto.getUsuario().getId())
				.build();
	}

	private Produto converter(ProdutoDTO dto) {
		Produto produto = new Produto();
		produto.setId(dto.getId());
		produto.setQuantidade(dto.getQuantidade());
		produto.setDescricao(dto.getDescricao());
		produto.setValor(dto.getValor());
		produto.setDataCadastro(dto.getDataCadastro());

		Usuario usuario = usuarioService.obterUsuarioPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o id informado"));

		produto.setUsuario(usuario);

		return produto;
	}

}