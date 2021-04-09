package Modelo;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RecuperarContraseniaLogic 
{
	public int GenerarCodigo() 
	{
		Random rnd = new Random();
        return ((rnd.nextInt(900000)) + 100000);
	}
	
	public void EnviarCorreo(int cod, String emailReceptor) throws Exception 
	{
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(properties);
		
		String correoRemitente = "gimnasiojava@gmail.com";
		String contraseniaRemitente = "Java1234";
		String correoReceptor = emailReceptor;
		String asunto = "Recuperación de contraseña";
		String mensaje = "Código para recuperar contraseña: " + cod;
		
		MimeMessage message = new MimeMessage(session);
		Transport transport = null;
		try
		{
			message.setFrom(new InternetAddress(correoRemitente));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
			message.setSubject(asunto);
			message.setText(mensaje);

			transport = session.getTransport("smtp");
			transport.connect(correoRemitente, contraseniaRemitente);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		} 
		catch (MessagingException e) 
		{
			Exception excepcionManejada = new Exception("Error al enviar correo a " + emailReceptor, e);
			throw excepcionManejada;
		}
		finally
		{
			if(transport != null) transport.close();
		}
	}
}