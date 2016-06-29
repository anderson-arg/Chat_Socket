package br.edu.ifpb.si.pd.chat.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifpb.si.pd.chat.models.Usuario;
import br.edu.ifpb.si.pd.chat.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Servidor {
	private ServerSocket serverSocket;
	private Map<String, Usuario> usuarios;
	
	public Servidor(int porta) {
		try {
			this.serverSocket = new ServerSocket(porta);
			this.usuarios = new HashMap<String, Usuario>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario getInstanceUsuario(){
		Socket s = null;
		try {
			s = this.serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cliente c = new Cliente();
		c.setSocket(s);
		Usuario u = new Usuario();
		u.setCliente(c);
		return u;
	}
	
	public boolean existe(Usuario usuario){
		if(this.usuarios.containsKey(usuario.getNome())){
			return true;
		}else{
			return false;
		}
	}
	
	public void enviarParaTodos(Usuario u){
		for(Map.Entry<String, Usuario> kv : this.usuarios.entrySet()){
			if(!u.equals(kv.getValue()))kv.getValue().getCliente().enviar((u.getCliente().getSocket().getInetAddress()+":"+u.getCliente().getSocket().getPort()+" "+u.getNome()+" diz: "+u.getMsg()+" "+DateUtil.dataAtual()));
		}
	}
	
	public void listarUsuarios(Usuario usuario){
		String msg="Usuarios Online:"+this.usuarios.size()+"\n";
		for(String s : this.usuarios.keySet()){
			msg+= s+"\n";
		}
		usuario.getCliente().enviar(msg);
	}
	
	public void bye(Usuario u){
		for(Map.Entry<String, Usuario> kv : this.usuarios.entrySet()){
			if(!u.equals(kv.getValue()))kv.getValue().getCliente().enviar((u.getCliente().getSocket().getInetAddress()+":"+u.getCliente().getSocket().getPort()+" "+u.getNome()+" saiu do chat... "+DateUtil.dataAtual()));
		}
		this.usuarios.remove(u.getNome());
		u.getCliente().close();
	}
	
	public void notificarEntrada(Usuario u){
		for(Map.Entry<String, Usuario> kv : this.usuarios.entrySet()){
			if(!u.equals(kv.getValue()))kv.getValue().getCliente().enviar((u.getCliente().getSocket().getInetAddress()+":"+u.getCliente().getSocket().getPort()+" "+u.getNome()+" entrou no chat... "+DateUtil.dataAtual()));
		}
	}
	
	public void sendUser(Usuario u, String nome){
		for(Map.Entry<String, Usuario> kv : this.usuarios.entrySet()){
			if(nome.equals(kv.getKey())){
				kv.getValue().getCliente().enviar((u.getCliente().getSocket().getInetAddress()+":"+u.getCliente().getSocket().getPort()+" "+u.getNome()+" diz[privado]: "+u.getMsg()+" "+DateUtil.dataAtual()));
			}
		}
	}
	
	public boolean rename(Usuario u, String novoNome){
		if(existe(u) && !this.usuarios.containsKey(novoNome)){
			this.usuarios.remove(u.getNome());
			u.setNome(novoNome);
			this.usuarios.put(u.getNome(), u);
			return true;
		}else{
			return false;
		}
	}
	
	public void close(){
		try {
			this.serverSocket.close();
			this.usuarios = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
