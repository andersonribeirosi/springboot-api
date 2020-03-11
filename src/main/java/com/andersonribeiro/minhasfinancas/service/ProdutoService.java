package com.andersonribeiro.minhasfinancas.service;

import java.util.List;
import java.util.Optional;


import com.andersonribeiro.minhasfinancas.model.entity.Produto;


public interface ProdutoService {

	Produto salvar(Produto produto);
	
	Produto atualizar(Produto produto);
	
	void deletar(Produto produto);
	
	List<Produto> buscar(Produto produtoFiltro);
	
	void validarCampos(Produto produto);
	
	Optional<Produto> obterPorId(Long id);
	
//	BigDecimal obterSaldoPorTipoEusuario(Long id);
}
