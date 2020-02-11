package com.andersonribeiro.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andersonribeiro.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
