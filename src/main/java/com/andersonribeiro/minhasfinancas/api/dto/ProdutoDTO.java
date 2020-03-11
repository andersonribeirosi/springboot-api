package com.andersonribeiro.minhasfinancas.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

	  private Long id;
      private String descricao;
      private BigDecimal valor;
      private Integer quantidade;
      private Long usuario;
      private LocalDate dataCadastro;
}
