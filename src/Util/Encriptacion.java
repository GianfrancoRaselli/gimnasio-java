package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public abstract class Encriptacion 
{
	public static String Encriptar(String clave) throws Exception
	{
		String secretKey = "encriptacion";
		
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] keyBytes = Arrays.copyOf(md.digest(secretKey.getBytes("utf-8")), 24);
			
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] plainTextBytes = clave.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			
			return new String(base64Bytes);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			Exception excepcionManejada = new Exception("Error al encriptar contraseña", e);
			throw excepcionManejada;
		}
	}
	
	public static String Desencriptar(String claveEncriptada) throws Exception
	{
		String secretKey = "encriptacion";
		
		try 
		{
			byte[] message = Base64.decodeBase64(claveEncriptada.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] keyBytes = Arrays.copyOf(md.digest(secretKey.getBytes("utf-8")), 24);
			
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] plainText = decipher.doFinal(message);
			
			return new String(plainText, "UTF-8");
		} 
		catch (Exception e) 
		{
			Exception excepcionManejada = new Exception("Error al desencriptar contraseña", e);
			throw excepcionManejada;
		}
	}
}