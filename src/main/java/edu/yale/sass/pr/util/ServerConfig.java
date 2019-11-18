package edu.yale.sass.pr.util;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {
	
	static Properties properties = new Properties();
	
	public ServerConfig(){}
	
	static{
		InputStream input =null;
		try{
			input = new FileInputStream(System.getenv("CATALINA_HOME")+"/conf/roster.properties");
			properties.load(input);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public static String get(String key){
		return (String)properties.get(key);
	}
	
	public static String getString(String key, String defaultValue){
		String value = (String)properties.get(key);
		if (value==null){
			value= defaultValue;
		}
		return value;
	}
	
	public static int getInt(String key, int defaultValue){
		int value = defaultValue;
		try{
			value = Integer.parseInt(properties.get(key)+"");
		}
		catch(Exception ignore){}
		return value;
	}
	
	public static boolean getBoolean(String key, boolean defaultValue){
		boolean value = false;
		String val = (String) properties.get(key);
		if (val!=null){
			if (val.equalsIgnoreCase("true")){
				value = true;
			}
			else{
				value = false;
			}
		}
		else{
			value = defaultValue;
		}
		return value;
	}
}
