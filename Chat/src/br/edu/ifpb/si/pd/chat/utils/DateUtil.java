package br.edu.ifpb.si.pd.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String dataAtual(){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String s = format.format(new Date());		
		return s;
	}
}
