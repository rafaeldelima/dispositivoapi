package br.com.fiap.dispositivoapi.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.dispositivoapi.api.model.Usuario;

public interface UsuarioRepository extends  MongoRepository<Usuario, String> {

	List<Usuario> findByTenantId(String tenantId);
	Usuario findById(String id);
	List<Usuario> findByEmail(String email);
}
