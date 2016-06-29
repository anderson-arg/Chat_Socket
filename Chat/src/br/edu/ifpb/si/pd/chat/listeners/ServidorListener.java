package br.edu.ifpb.si.pd.chat.listeners;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;

import br.edu.ifpb.si.pd.chat.enums.Tokens;
import br.edu.ifpb.si.pd.chat.models.Usuario;
import br.edu.ifpb.si.pd.chat.services.Servidor;

public class ServidorListener extends Thread {

	private Servidor servidor;
	private Usuario usuario;
	
	public ServidorListener(Servidor servidor, Usuario usuario) {
		this.servidor = servidor;
		this.usuario = usuario;
	}
	
	@Override
	public void run() {
		while(true){
			this.usuario.getCliente().enviar("Digite o seu nome:");
			
			try {
				this.usuario.setNome(this.usuario.getCliente().ler());
			} catch (IOException e) {
				return;
			}

			if(this.servidor.existe(this.usuario)){
				this.usuario.getCliente().enviar("Usuario existente, digite novamente...");
				continue;
			}
			this.servidor.getUsuarios().put(this.usuario.getNome(), this.usuario);
			break;
		}
		
		this.servidor.notificarEntrada(this.usuario);
		
		while(true){
			this.usuario.getCliente().enviar(this.usuario.getNome()+" diz:");
			Map<String, Matcher> token;
			
			String texto;
			try {
				texto = this.usuario.getCliente().ler();
			} catch (IOException e1) {
				break;
			}
			
			try {
				token = Tokens.getToken(texto);
				if(token.containsKey(Tokens.SEND_ALL.name())){
					this.usuario.setMsg(token.get(Tokens.SEND_ALL.name()).group(2));
					this.servidor.enviarParaTodos(this.usuario);
				}else if(token.containsKey(Tokens.SEND_USER.name())){
					if(this.servidor.existe(new Usuario(token.get(Tokens.SEND_USER.name()).group(2)))){
						this.usuario.setMsg(token.get(Tokens.SEND_USER.name()).group(3));
						this.servidor.sendUser(this.usuario, token.get(Tokens.SEND_USER.name()).group(2));
					}else
						this.usuario.getCliente().enviar("Usuario inexistente!");
				}else if(token.containsKey(Tokens.LIST.name())){
					this.servidor.listarUsuarios(this.usuario);
				}else if(token.containsKey(Tokens.RENAME.name())){
					if(this.servidor.rename(this.usuario, token.get(Tokens.RENAME.name()).group(2))){
						this.usuario.getCliente().enviar("Nome alterado com sucesso!");
					}else{
						this.usuario.getCliente().enviar("Nome existente, digite outro!");
					}
				}else if(token.containsKey(Tokens.HELP.name())){
					this.usuario.getCliente().enviar("send -all <mensagem> : Para enviar uma mensagem para todos os usuarios online\n"
							+ "send user <nome_usuario> <mensagem> : Para mandar uma mensagem privada\n"
							+ "list : Para listar todos os usuarios online\n"
							+ "rename <novo_nome> : Para renomear\n"
							+ "bye : Para sair do chat");
				}else if(token.containsKey(Tokens.BYE.name())){
					this.servidor.bye(this.usuario);
					break;
				}

			} catch (Exception e) {
				this.usuario.getCliente().enviar(e.getMessage());
			}

		}
	}

}
