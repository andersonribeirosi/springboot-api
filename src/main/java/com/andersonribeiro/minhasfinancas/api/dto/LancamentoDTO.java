package com.andersonribeiro.minhasfinancas.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {

	private Long id;
	private String descricao;
	private Integer ano;
	private Integer mes;
	private Long usuario;
	private BigDecimal valor;
	private String tipo;
	private String status;

}
