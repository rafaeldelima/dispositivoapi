package br.com.fiap.dispositivoapi.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.dispositivoapi.api.model.Cliente;

public interface ClienteRepository extends  MongoRepository<Cliente, String> {
	Cliente findById(String id);
	List<Cliente> findByNome(String nome);
}
