package br.edu.ifpb.si.pd.chat.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Tokens {
	SEND_ALL("(send -all) ([\\W\\p{Print}]+)"),
	SEND_USER("(send user) ([\\w]+) ([\\W\\p{Print}]+)"),
	LIST("list"),
	HELP("help"),
	RENAME("(rename) ([\\w]+)"),
	BYE("bye");
	
	public final String regex;
	
	private Tokens(String regex){
		this.regex = regex;
	}
	
	public static Map<String, Matcher> getToken(String texto) throws Exception{
		Map<String, Matcher> map = new HashMap<String, Matcher>();
		for(Tokens t : Tokens.values()){
			if(Pattern.matches(t.regex, texto)){
				Matcher m = Pattern.compile(t.regex).matcher(texto);
				m.find(); 
				map.put(t.name(), m);
			}
		}
		if(map.isEmpty()) throw new Exception("Erro de Sintaxe... Digite 'help'");
		return map;
	}
}
