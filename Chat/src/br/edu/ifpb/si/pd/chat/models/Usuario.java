package br.edu.ifpb.si.pd.chat.models;

import br.edu.ifpb.si.pd.chat.services.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Usuario {
	private Cliente cliente;
	private String nome;
	private String msg;
	
	public Usuario(){}
	
	public Usuario(String nome) {
		this.nome = nome;
	}
	
}
