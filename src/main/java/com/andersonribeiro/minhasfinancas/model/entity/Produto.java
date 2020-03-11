package com.andersonribeiro.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produto", schema = "financas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
	
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @Column(name = "descricao")
      private String descricao;
	  
	  @Column(name = "quantidade")
      private Integer quantidade;
	  
	  @Column(name = "valor")
      private BigDecimal valor;
	  
	  @Column(name = "data_cadastro")
	  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
      private LocalDate dataCadastro;
      
	  @ManyToOne
	  @JoinColumn(name = "id_usuario")
      private Usuario usuario;

}
