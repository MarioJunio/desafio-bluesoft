package br.com.bluesoft.desafio.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecoProdutoDTO {

	private BigDecimal preco;
	
	@JsonProperty(value = "quantidade_minima")
	private Integer quantidadeMinima;
}
