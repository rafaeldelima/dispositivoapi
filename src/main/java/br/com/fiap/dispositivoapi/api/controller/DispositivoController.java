package br.com.fiap.dispositivoapi.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dispositivoapi.api.controller.dto.Response;
import br.com.fiap.dispositivoapi.api.model.Dispositivo;
import br.com.fiap.dispositivoapi.api.service.DispositivoService;


@RestController
@CrossOrigin
@RequestMapping("/dispositivo")
public class DispositivoController {

	@Autowired
	private DispositivoService dispositivoService;
	
	@GetMapping
	ResponseEntity<Response<List<Dispositivo>>> listarTodos() {
		Response<List<Dispositivo>> resposta = new Response<List<Dispositivo>>();
		List<Dispositivo> dispositivos = dispositivoService.listarDispositivos();
		
		if (dispositivos != null && !dispositivos.isEmpty()) {
			resposta.setData(dispositivos);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	ResponseEntity<Response<Dispositivo>> inserir(@RequestBody Dispositivo dispositivo) {
		Response<Dispositivo> resposta = new Response<Dispositivo>();
		
		this.validarDadosDispositivo(dispositivo, resposta);			
		if(!resposta.getErrors().isEmpty()) {
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Dispositivo dispositivoInserido = dispositivoService.criarOuAtualizarDispositivo(dispositivo);
		
		if (dispositivoInserido != null) {
			resposta.setData(dispositivoInserido);
			return new ResponseEntity<>(resposta, HttpStatus.CREATED);
		} else {
			resposta.getErrors().add("Ocorreu um erro ao tentar inserir o Dispositivo");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void validarDadosDispositivo(Dispositivo dispositivo, Response<Dispositivo> resposta) {
		
		if(StringUtils.isEmpty(dispositivo.getUuid())) {
			resposta.getErrors().add("Necessario informar o UUID do dispositivo");
		}
		if(dispositivo.getCliente() == null || StringUtils.isEmpty(dispositivo.getCliente().getId())) {
			resposta.getErrors().add("Necessario informar o cliente do dispositivo");
		}
		if(dispositivo.getTenant() == null || StringUtils.isEmpty(dispositivo.getTenant().getId())) {
			resposta.getErrors().add("Necessario informar o tenant do dispositivo");
		}
		
	}

	@PutMapping
	ResponseEntity<Response<Dispositivo>> atualizar(@RequestBody Dispositivo dispositivo) {
		Response<Dispositivo> resposta = new Response<Dispositivo>();
		if(dispositivo.getId() != null) {
			Dispositivo dispositivoBase = dispositivoService.buscarDispositivoPorId(dispositivo.getId());
			if(dispositivoBase == null) {
				resposta.getErrors().add("Nao foi encontrado um Dispositivo com este id");
				return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			Dispositivo dispositivoInserido = dispositivoService.criarOuAtualizarDispositivo(dispositivo);	
			resposta.setData(dispositivoInserido);
			return new ResponseEntity<>(resposta, HttpStatus.OK);	
			
		}else{
			resposta.getErrors().add("Ocorreu um erro ao tentar atualizar o Dispositivo");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Response<Dispositivo>> buscarDispositivoPorId(@PathVariable("id") String id) {
		Response<Dispositivo> resposta = new Response<Dispositivo>();
		Dispositivo dispositivos = dispositivoService.buscarDispositivoPorId(id);
		
		if (dispositivos != null) {
			resposta.setData(dispositivos);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/uuid/{uuid}")
	ResponseEntity<Response<List<Dispositivo>>> buscarDispositivosPorUuid(@PathVariable("uuid") String uuid) {
		Response<List<Dispositivo>> resposta = new Response<List<Dispositivo>>();
		List<Dispositivo> dispositivos = dispositivoService.buscarDispositivoPorUuid(uuid);
		
		if (dispositivos != null && !dispositivos.isEmpty()) {
			resposta.setData(dispositivos);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/tenant/{tenantId}")
	ResponseEntity<Response<List<Dispositivo>>> buscarDispositivosPorTenantId(@PathVariable("tenantId") String tenantId) {
		Response<List<Dispositivo>> resposta = new Response<List<Dispositivo>>();
		List<Dispositivo> dispositivos = dispositivoService.listarDispositivosTenant(tenantId);
		
		if (dispositivos != null && !dispositivos.isEmpty()) {
			resposta.setData(dispositivos);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/{clienteId}")
	ResponseEntity<Response<List<Dispositivo>>> buscarDispositivosPorClienteId(@PathVariable("clienteId") String clienteId) {
		Response<List<Dispositivo>> resposta = new Response<List<Dispositivo>>();
		List<Dispositivo> dispositivos = dispositivoService.listarDispositivosCliente(clienteId);
		
		if (dispositivos != null && !dispositivos.isEmpty()) {
			resposta.setData(dispositivos);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
}
