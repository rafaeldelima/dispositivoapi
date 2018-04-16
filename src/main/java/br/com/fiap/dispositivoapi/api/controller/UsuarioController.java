package br.com.fiap.dispositivoapi.api.controller;

import java.util.List;

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
import br.com.fiap.dispositivoapi.api.model.Tenant;
import br.com.fiap.dispositivoapi.api.model.Usuario;
import br.com.fiap.dispositivoapi.api.service.TenantService;
import br.com.fiap.dispositivoapi.api.service.UsuarioService;


@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TenantService tenantService;
	
	@GetMapping
	ResponseEntity<Response<List<Usuario>>> listarTodos() {
		Response<List<Usuario>> resposta = new Response<List<Usuario>>();
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		
		if (usuarios != null && !usuarios.isEmpty()) {
			usuarios.forEach(usuar -> usuar.setSenha(null));
			resposta.setData(usuarios);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/login")
	ResponseEntity<Response<Usuario>> login(@RequestBody Usuario usuario) {
		Response<Usuario> resposta = new Response<Usuario>();
		Usuario usuarioBase = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());
		
		if (usuarioBase != null) {
			if(usuarioBase.getSenha().equals(usuario.getSenha())) {
				usuarioBase.setSenha(null);
				resposta.setData(usuarioBase);
				return new ResponseEntity<>(resposta, HttpStatus.OK);
			}else {
				resposta.getErrors().add("Senha incorreta");
				return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
			}
		} else {
			resposta.getErrors().add("Usuario não encontrado para o email " + usuario.getEmail());
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping
	ResponseEntity<Response<Usuario>> inserir(@RequestBody Usuario usuario) {
		Response<Usuario> resposta = new Response<Usuario>();
		
		this.validarDadosCriarUsuario(usuario, resposta);
		
		if(!resposta.getErrors().isEmpty()) {
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Tenant tenant = tenantService.buscarTenantPorId(usuario.getTenant().getId());
		if(tenant == null || tenant.getId() == null) {
			resposta.getErrors().add("Nao encontramos um Tenant para o id informado");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Usuario usuarioInserido = usuarioService.criarOuAtualizarUsuario(usuario);
		
		if (usuarioInserido != null) {
			usuarioInserido.setSenha(null);
			resposta.setData(usuarioInserido);
			return new ResponseEntity<>(resposta, HttpStatus.CREATED);
		} else {
			resposta.getErrors().add("Ocorreu um erro ao tentar inserir o Usuario");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void validarDadosCriarUsuario(Usuario usuario, Response<Usuario> resposta) {
		if(StringUtils.isEmpty(usuario.getNome())) {
			resposta.getErrors().add("Necessario informar um nome para o usuario");
		}
		if(StringUtils.isEmpty(usuario.getEmail())) {
			resposta.getErrors().add("Necessario informar um email para o usuario");
		}
		if(StringUtils.isEmpty(usuario.getSenha())) {
			resposta.getErrors().add("Necessario informar uma senha para o usuario");
		}
		if(usuario.getTenant() == null || usuario.getTenant().getId() == null) {
			resposta.getErrors().add("Necessario informar o id do tenant para o usuario");
		}
		
	}

	@PutMapping
	ResponseEntity<Response<Usuario>> atualizar(@RequestBody Usuario usuario) {
		Response<Usuario> resposta = new Response<Usuario>();
		if(usuario.getId() != null) {
			Usuario usuarioBase = usuarioService.buscarUsuarioPorId(usuario.getId());
			if(usuarioBase == null) {
				resposta.getErrors().add("Nao foi encontrado um Usuario com este id");
				return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			Usuario usuarioInserido = usuarioService.criarOuAtualizarUsuario(usuario);	
			usuarioInserido.setSenha(null);
			resposta.setData(usuarioInserido);
			return new ResponseEntity<>(resposta, HttpStatus.OK);	
			
		}else{
			resposta.getErrors().add("Ocorreu um erro ao tentar atualizar o Usuario");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/email/{email}")
	ResponseEntity<Response<Usuario>> buscarUsuario(@PathVariable("email") String email) {
		Response<Usuario> resposta = new Response<Usuario>();
		Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
		
		if (usuario != null) {
			usuario.setSenha(null);
			resposta.setData(usuario);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/tenant/{tenant}")
	ResponseEntity<Response<List<Usuario>>> listarTodosTenanty(@PathVariable("tenant") String tenant) {
		Response<List<Usuario>> resposta = new Response<List<Usuario>>();
		List<Usuario> usuarios = usuarioService.listarUsuariosTenant(tenant);
		
		if (usuarios != null && !usuarios.isEmpty()) {
			usuarios.forEach(usuar -> usuar.setSenha(null));
			resposta.setData(usuarios);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
}
