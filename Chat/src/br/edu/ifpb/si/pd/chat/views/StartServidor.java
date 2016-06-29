package br.edu.ifpb.si.pd.chat.views;

import br.edu.ifpb.si.pd.chat.listeners.ServidorListener;
import br.edu.ifpb.si.pd.chat.services.Servidor;

public class StartServidor {
	public static void main(String[] args) {
		Servidor s = new Servidor(9999);
		while(true){
			new ServidorListener(s,s.getInstanceUsuario()).start();
		}
	}
}
