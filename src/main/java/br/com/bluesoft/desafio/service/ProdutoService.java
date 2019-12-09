package br.com.bluesoft.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.ProdutoRepository;

@Repository
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto buscarPorGtin(String gtin) {
		return produtoRepository.findOne(gtin);
	}

}
