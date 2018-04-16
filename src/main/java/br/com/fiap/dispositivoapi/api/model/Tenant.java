package br.com.fiap.dispositivoapi.api.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tenant {

	@Id
	private String id;
	private String nome;
	private boolean flagAtivo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isFlagAtivo() {
		return flagAtivo;
	}
	public void setFlagAtivo(boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}
	
	
}
