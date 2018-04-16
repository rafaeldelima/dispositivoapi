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
import br.com.fiap.dispositivoapi.api.model.Tenant;
import br.com.fiap.dispositivoapi.api.service.TenantService;


@RestController
@CrossOrigin
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	private TenantService tenantService;
	
	@GetMapping
	ResponseEntity<Response<List<Tenant>>> listarTodos() {
		Response<List<Tenant>> resposta = new Response<List<Tenant>>();
		List<Tenant> tenants = tenantService.listarTenants();
		
		if (tenants != null && !tenants.isEmpty()) {
			resposta.setData(tenants);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	ResponseEntity<Response<Tenant>> inserir(@RequestBody Tenant tenant) {
		Response<Tenant> resposta = new Response<Tenant>();
		List<Tenant> tenantBase = tenantService.buscarTenantPorNome(tenant.getNome());
		if(tenantBase != null && !tenantBase.isEmpty()) {
			resposta.getErrors().add("Ja existe um tenant com o mesmo nome");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
		
			Tenant tenantInserido = tenantService.criarOuAtualizar(tenant);
			
			if (tenantInserido != null) {
				resposta.setData(tenantInserido);
				return new ResponseEntity<>(resposta, HttpStatus.CREATED);
			} else {
				resposta.getErrors().add("Ocorreu um erro ao tentar inserir o Tenant");
				return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@PutMapping
	ResponseEntity<Response<Tenant>> atualizar(@RequestBody Tenant tenant) {
		Response<Tenant> resposta = new Response<Tenant>();
		if(tenant.getId() != null) {
			Tenant tenantBase = tenantService.buscarTenantPorId(tenant.getId());
			if(tenantBase == null) {
				resposta.getErrors().add("Nao foi encontrado um tenant com este id");
				return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			Tenant tenantAtualizado = tenantService.criarOuAtualizar(tenant);
			resposta.setData(tenantAtualizado);
			return new ResponseEntity<>(resposta, HttpStatus.OK);	
			
		}else{
			resposta.getErrors().add("Voce deve informar um id no tenant");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{tenantId}")
	ResponseEntity<Response<Tenant>> buscarTenant(@PathVariable("tenantId") String tenantId) {
		Response<Tenant> resposta = new Response<Tenant>();
		Tenant tenantBase = tenantService.buscarTenantPorId(tenantId);
		
		if (tenantBase != null) {
			resposta.setData(tenantBase);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nao encontramos nenhum tenant com este id");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
}
