package com.andersonribeiro.minhasfinancas.exceptions;

public class ErroAutenticacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErroAutenticacaoException(String mensagem) {
		super(mensagem);
	}

}
