package br.com.bluesoft.desafio.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorProdutoDTO {

	private String cnpj;
	private String nome;
	private List<PrecoProdutoDTO> precos = new ArrayList<>();
}
