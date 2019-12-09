package br.com.bluesoft.desafio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItenDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProdutoDTO produto;
	private Integer quantidade;
	private BigDecimal preco;
	private BigDecimal total;
}
