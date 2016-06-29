package br.edu.ifpb.si.pd.chat.listeners;
import java.io.IOException;

import br.edu.ifpb.si.pd.chat.services.Cliente;

public class ClienteListener extends Thread{
	
	private Cliente cliente;

	public ClienteListener(Cliente c) {
		this.cliente = c;
	}
	
	public void run() {
		while(true){
			try {
				System.out.println(this.cliente.ler());
			} catch (IOException e) {
				this.cliente.close();
				break;
			}
		}
		System.out.println("Você saiu do chat!");
	}

}
