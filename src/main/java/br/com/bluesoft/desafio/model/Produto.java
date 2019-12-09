package br.com.bluesoft.desafio.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "gtin")
@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    private String gtin;
    private String nome;
    
    public Produto(String gtin) {
    	this.gtin = gtin;
    }

}
