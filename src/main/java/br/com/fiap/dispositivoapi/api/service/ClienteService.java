package br.com.fiap.dispositivoapi.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.dispositivoapi.api.model.Cliente;
import br.com.fiap.dispositivoapi.api.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente criarOuAtualizar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public List<Cliente> listarTodos(){
		return clienteRepository.findAll();
	}
	
	public Cliente buscarPorId(String id){
		return clienteRepository.findById(id);
	}	
	
	public List<Cliente> buscarPorNome(String nome){
		return clienteRepository.findByNome(nome);
	}	
	
	
	
}
