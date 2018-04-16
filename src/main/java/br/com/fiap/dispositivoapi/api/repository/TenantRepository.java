package br.com.fiap.dispositivoapi.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.dispositivoapi.api.model.Tenant;

public interface TenantRepository extends  MongoRepository<Tenant, String> {

	Tenant findById(String id);
	List<Tenant> findByNome(String nome);
}
