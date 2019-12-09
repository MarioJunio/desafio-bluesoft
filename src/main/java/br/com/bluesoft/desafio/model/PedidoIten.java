package br.com.bluesoft.desafio.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data()
@Entity
@Table(name = "Pedido_Item")
public class PedidoIten implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PedidoItenId id;
	private Integer quantidade;
	private BigDecimal preco;
	private BigDecimal total;
	
}
