package br.com.fiap.dispositivoapi.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.dispositivoapi.api.model.Dispositivo;

public interface DispositivoRepository extends  MongoRepository<Dispositivo, String> {

	Dispositivo findById(String id);	
	List<Dispositivo> findByUuid(String uuid);
	List<Dispositivo> findByTenantId(String tenantId);
	List<Dispositivo> findByClienteId(String clienteId);
	
}
