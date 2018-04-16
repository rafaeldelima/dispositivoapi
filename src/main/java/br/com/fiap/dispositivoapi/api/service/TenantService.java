package br.com.fiap.dispositivoapi.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.dispositivoapi.api.model.Tenant;
import br.com.fiap.dispositivoapi.api.repository.TenantRepository;

@Service
public class TenantService {

	@Autowired
	private TenantRepository tenantRepository;
	
	public Tenant criarOuAtualizar(Tenant tenant) {
		return tenantRepository.save(tenant);
	}
	
	public List<Tenant> listarTenants(){
		return tenantRepository.findAll();
	}
	
	public Tenant buscarTenantPorId(String id){
		return tenantRepository.findById(id);
	}	
	
	public List<Tenant> buscarTenantPorNome(String nome){
		return tenantRepository.findByNome(nome);
	}	
	
	
	
}
