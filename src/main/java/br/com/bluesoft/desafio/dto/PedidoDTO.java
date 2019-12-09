package br.com.bluesoft.desafio.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data()
public class PedidoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private FornecedorDTO fornecedor;
	private List<PedidoItenDTO> itens = new ArrayList<>();
	
}
