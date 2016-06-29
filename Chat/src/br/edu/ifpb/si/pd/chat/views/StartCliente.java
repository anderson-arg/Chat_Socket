package br.edu.ifpb.si.pd.chat.views;

import java.util.Scanner;

import br.edu.ifpb.si.pd.chat.listeners.ClienteListener;
import br.edu.ifpb.si.pd.chat.services.Cliente;

public class StartCliente {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Cliente cliente = new Cliente("localhost", 9999);
		new ClienteListener(cliente).start();
		
		while(!cliente.getSocket().isClosed()){
			cliente.enviar(s.nextLine());
		}
	}
}
