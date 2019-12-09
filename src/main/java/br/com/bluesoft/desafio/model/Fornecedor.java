package br.com.bluesoft.desafio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "cnpj")
@Entity
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cnpj;
	private String nome;
	
	@OneToMany(mappedBy = "fornecedor")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Fornecedor(String cnpj, String nome) {
		this.cnpj = cnpj;
		this.nome = nome;
	}
}
