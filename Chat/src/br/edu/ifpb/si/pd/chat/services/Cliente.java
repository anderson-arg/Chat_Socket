package br.edu.ifpb.si.pd.chat.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cliente {
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	
	public Cliente(){}
	
	public Cliente(String ip, int porta) {
		try {
			this.socket = new Socket(ip, porta);
			setDataStream(this.socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
		setDataStream(this.socket);
	}
	
	public void setDataStream(Socket socket){
		try {
			this.out = new DataOutputStream(socket.getOutputStream());
			this.in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String ler() throws IOException{
		String msg = null;
		msg = this.in.readUTF();
		return msg;
	}
	
	public void enviar(String msg){
		try {
			if(!this.socket.isClosed()) this.out.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			this.socket.close();
			this.out.close();
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
