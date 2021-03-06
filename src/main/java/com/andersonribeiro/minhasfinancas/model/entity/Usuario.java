package com.andersonribeiro.minhasfinancas.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

//Exemplo do uso do Lombok (Utilizando apenas a notação Data)
//Substitui o getters and setters tostring, haschcodeandequals

@Entity
@Table(name = "usuario", schema = "financas")
@Data
@Builder
public class Usuario {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	

}
