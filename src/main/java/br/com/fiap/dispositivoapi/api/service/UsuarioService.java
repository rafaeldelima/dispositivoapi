package br.com.fiap.dispositivoapi.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.dispositivoapi.api.model.Usuario;
import br.com.fiap.dispositivoapi.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario criarOuAtualizarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public List<Usuario> listarUsuariosTenant(String tenantId){
		return usuarioRepository.findByTenantId(tenantId);
	}	
	
	public Usuario buscarUsuarioPorId(String id){
		return usuarioRepository.findById(id);
	}	
	
	public List<Usuario> buscarUsuarioPorEmail(String email){
		return usuarioRepository.findByEmail(email);
	}	
	
}
