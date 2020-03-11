package com.andersonribeiro.minhasfinancas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.andersonribeiro.minhasfinancas.model.entity.Lancamento;
import com.andersonribeiro.minhasfinancas.model.entity.Produto;
import com.andersonribeiro.minhasfinancas.model.enums.StatusLancamento;
import com.andersonribeiro.minhasfinancas.model.enums.TipoLancamento;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query(value = "select sum(l.valor) from Lancamento l join l.usuario u "
			+ " where u.id = :idUsuario and l.tipo = :tipo and l.status = :status group by u ")
	BigDecimal obterSaldoPorTipoEusuarioEStatus(
			@Param("idUsuario") Long idUsuario, 
			@Param("tipo") TipoLancamento tipo,
			@Param("status") StatusLancamento status);
}