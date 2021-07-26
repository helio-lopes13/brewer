package com.algaworks.brewer.dto;

import java.math.BigDecimal;

public class Estoque {
	
	private Long totalItens;
	
	private BigDecimal valor;
	
	public Estoque() {
		
	}

	public Estoque(Long totalItens, BigDecimal valor) {
		this.totalItens = totalItens;
		this.valor = valor;
	}

	public Long getTotalItens() {
		return totalItens != null ? totalItens : 0L;
	}

	public void setTotalItens(Long totalItens) {
		this.totalItens = totalItens;
	}

	public BigDecimal getValor() {
		return valor != null ? valor : BigDecimal.ZERO;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
