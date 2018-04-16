package br.com.fiap.dispositivoapi.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.dispositivoapi.api.model.Dispositivo;
import br.com.fiap.dispositivoapi.api.repository.DispositivoRepository;

@Service
public class DispositivoService {

	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	public Dispositivo criarOuAtualizarDispositivo(Dispositivo dispositivo) {
		return dispositivoRepository.save(dispositivo);
	}
	
	public List<Dispositivo> listarDispositivos(){
		return dispositivoRepository.findAll();
	}
	
	public List<Dispositivo> listarDispositivosTenant(String tenantId){
		return dispositivoRepository.findByTenantId(tenantId);
	}	
	
	public List<Dispositivo> listarDispositivosCliente(String clienteId){
		return dispositivoRepository.findByClienteId(clienteId);
	}	
	
	public Dispositivo buscarDispositivoPorId(String id){
		return dispositivoRepository.findById(id);
	}	
	
	public List<Dispositivo> buscarDispositivoPorUuid(String uuid){
		return dispositivoRepository.findByUuid(uuid);
	}	
	
}
