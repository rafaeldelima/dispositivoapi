package br.com.fiap.dispositivoapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dispositivoapi.api.controller.dto.Response;
import br.com.fiap.dispositivoapi.api.model.Cliente;
import br.com.fiap.dispositivoapi.api.service.ClienteService;


@RestController
@CrossOrigin
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	ResponseEntity<Response<List<Cliente>>> listarTodos() {
		Response<List<Cliente>> resposta = new Response<List<Cliente>>();
		List<Cliente> clientes = clienteService.listarTodos();
		
		if (clientes != null && !clientes.isEmpty()) {
			resposta.setData(clientes);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	ResponseEntity<Response<Cliente>> inserir(@RequestBody Cliente cliente) {
		Response<Cliente> resposta = new Response<Cliente>();
		List<Cliente> clienteBase = clienteService.buscarPorNome(cliente.getNome());
		if(clienteBase != null && !clienteBase.isEmpty()) {
			resposta.getErrors().add("Ja existe um cliente com o mesmo nome");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
		
			Cliente clienteInserido = clienteService.criarOuAtualizar(cliente);
			
			if (clienteInserido != null) {
				resposta.setData(clienteInserido);
				return new ResponseEntity<>(resposta, HttpStatus.CREATED);
			} else {
				resposta.getErrors().add("Ocorreu um erro ao tentar inserir o Cliente");
				return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@PutMapping
	ResponseEntity<Response<Cliente>> atualizar(@RequestBody Cliente cliente) {
		Response<Cliente> resposta = new Response<Cliente>();
		if(cliente.getId() != null) {
			Cliente clienteBase = clienteService.buscarPorId(cliente.getId());
			if(clienteBase == null) {
				resposta.getErrors().add("Nao foi encontrado um cliente com este id");
				return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			Cliente clienteAtualizado = clienteService.criarOuAtualizar(cliente);
			resposta.setData(clienteAtualizado);
			return new ResponseEntity<>(resposta, HttpStatus.OK);	
			
		}else{
			resposta.getErrors().add("Voce deve informar um id no cliente");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{clienteId}")
	ResponseEntity<Response<Cliente>> buscarCliente(@PathVariable("clienteId") String clienteId) {
		Response<Cliente> resposta = new Response<Cliente>();
		Cliente clienteBase = clienteService.buscarPorId(clienteId);
		
		if (clienteBase != null) {
			resposta.setData(clienteBase);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nao encontramos nenhum cliente com este id");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
}
