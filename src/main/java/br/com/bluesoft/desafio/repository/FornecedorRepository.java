package br.com.bluesoft.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.bluesoft.desafio.model.Fornecedor;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, String> {

}
