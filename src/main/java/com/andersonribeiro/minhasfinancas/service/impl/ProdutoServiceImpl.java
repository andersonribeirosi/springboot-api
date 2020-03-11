package com.andersonribeiro.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andersonribeiro.minhasfinancas.exceptions.RegraNegocioException;
import com.andersonribeiro.minhasfinancas.model.entity.Produto;
import com.andersonribeiro.minhasfinancas.model.enums.StatusLancamento;
import com.andersonribeiro.minhasfinancas.model.repository.ProdutoRepository;
import com.andersonribeiro.minhasfinancas.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private ProdutoRepository repository;
	
	public ProdutoServiceImpl(ProdutoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Produto salvar(Produto produto) {
		validarCampos(produto);
		return repository.save(produto);
	}

	@Override
	@Transactional
	public Produto atualizar(Produto produto) {
		Objects.requireNonNull(produto.getId());
		validarCampos(produto);
		return repository.save(produto);
	}

	@Override
	@Transactional
	public void deletar(Produto produto) {
		Objects.requireNonNull(produto.getId());
		repository.delete(produto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produto> buscar(Produto produtoFiltro) {
		Example example = Example.of(produtoFiltro,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void validarCampos(Produto produto) {
		if (produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Descrição válida");
		}

		if (produto.getUsuario() == null || produto.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um Usuário");
		}

		if (produto.getValor() == null || produto.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um Valor válido");
		}

	}

	@Override
	public Optional<Produto> obterPorId(Long id) {
		return repository.findById(id);
	}

}
