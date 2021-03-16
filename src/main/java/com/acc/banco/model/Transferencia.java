package com.acc.banco.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {

	@NotNull(message = "Campo obrigatório..")
	private BigDecimal valor;

	@Valid
	private ContaCorrente contaOrigem;

	@Valid
	private ContaCorrente contaDestino;
	
}
