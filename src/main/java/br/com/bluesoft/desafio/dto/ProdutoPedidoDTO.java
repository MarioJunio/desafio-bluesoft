package br.com.bluesoft.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedidoDTO {
	
	private String gtin;
	private Integer quantidade;

}
