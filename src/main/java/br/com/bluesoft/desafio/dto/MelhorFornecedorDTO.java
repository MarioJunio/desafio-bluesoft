package br.com.bluesoft.desafio.dto;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.bluesoft.desafio.model.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MelhorFornecedorDTO {
	
	private Optional<Fornecedor> fornecedor;
	private BigDecimal menorPreco;
}
