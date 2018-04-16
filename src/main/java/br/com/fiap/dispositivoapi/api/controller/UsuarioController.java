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
import br.com.fiap.dispositivoapi.api.model.Usuario;
import br.com.fiap.dispositivoapi.api.service.UsuarioService;


@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	ResponseEntity<Response<List<Usuario>>> listarTodos() {
		Response<List<Usuario>> resposta = new Response<List<Usuario>>();
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		
		if (usuarios != null && !usuarios.isEmpty()) {
			resposta.setData(usuarios);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	ResponseEntity<Response<Usuario>> inserir(@RequestBody Usuario usuario) {
		Response<Usuario> resposta = new Response<Usuario>();
		Usuario usuarioInserido = usuarioService.criarOuAtualizarUsuario(usuario);
		
		if (usuarioInserido != null) {
			resposta.setData(usuarioInserido);
			return new ResponseEntity<>(resposta, HttpStatus.CREATED);
		} else {
			resposta.getErrors().add("Ocorreu um erro ao tentar inserir o Usuario");
			return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
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
		List<Usuario> usuarios = usuarioService.buscarUsuarioPorEmail(email);
		
		if (usuarios != null && !usuarios.isEmpty()) {
			resposta.setData(usuarios.get(0));
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
			resposta.setData(usuarios);
			return new ResponseEntity<>(resposta, HttpStatus.OK);
		} else {
			resposta.getErrors().add("Nenhum registro encontrado");
			return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
		}
	}
}
