package com.eprogramar.sendmail;

import java.io.File;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(){
		
		try{
//	        String recipientAddress = "fabianogoes@gmail.com";
//	        String[] cc = {"diegolirio.dl@gmail.com", "fgoes@dellavolpe.com.br"};
//	        String subject = "Spring Send Mail";
//	        String message = "Teste Envio de Email \n";
//	        message += "Se liga negão!!!";
	         
//	        // prints debug info
//	        System.out.println("To: " + recipientAddress);
//	        System.out.println("Subject: " + subject);
//	        System.out.println("Message: " + message);
//	         
//	        // creates a simple e-mail object
//	        SimpleMailMessage email = new SimpleMailMessage();
//	        email.setTo(recipientAddress);
//	        email.setCc(cc);
//	        email.setSubject(subject);
//	        email.setText(message);
//
//	        // sends the e-mail
//	        mailSender.send(email);
	        
			System.out.println( "Configurando Email...." );
			
	        MimeMessage mimeMessage = mailSender.createMimeMessage();

	        Address emailFrom = new InternetAddress("fabianogoes@gmail.com");
	        Address emailTo = new InternetAddress("fabianogoes@gmail.com");
	        Address emailCc1 = new InternetAddress("diegolirio.dl@gmail.com");
	        Address emailCc2 = new InternetAddress("fgoes@dellavolpe.com.br");
	        Address emailCc3 = new InternetAddress("ddamaceno@dellavolpe.com.br");
	        
	        mimeMessage.setFrom( emailFrom );
	        mimeMessage.addRecipient(RecipientType.TO, emailTo);
	        mimeMessage.addRecipient(RecipientType.TO, emailCc1);
	        mimeMessage.addRecipient(RecipientType.TO, emailCc2);
	        mimeMessage.addRecipient(RecipientType.TO, emailCc3);
	        
	        mimeMessage.setSentDate( new Date() );
	        mimeMessage.setSubject("Envio de E-Mail com Spring");
	        
	        String htmlMessage = "<font color='red'>Envio de E-Mail com Spring</font>";
	        htmlMessage += "<hr/>";
	        htmlMessage += "<h3>Corpo do Email em HTML</h3>";
	        htmlMessage += "<h5>Email com arquivo anexo!</h5>";
	        htmlMessage += "<hr/>";
	        htmlMessage += "Agora sim ;) \\o/";
	        
	        MimeBodyPart attachment0 = new MimeBodyPart();
	        attachment0.setContent(htmlMessage,"text/html; charset=UTF-8");
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(attachment0);
	        
	        String pathname = "/Users/fabiano/Imagens/mercadolivre-mao.gif";//pode conter o caminho
	        File file = new File(pathname);
	        MimeBodyPart attachment1 = new MimeBodyPart();  
	        attachment1.setDataHandler(new DataHandler(new FileDataSource(file)));
	        attachment1.setFileName(file.getName());
	        multipart.addBodyPart(attachment1);

	        mimeMessage.setContent(multipart);	        
	        
	        System.out.println( "Iniciando Envio do Email..." );
	        mailSender.send(mimeMessage);
	        
	        System.out.println( "Email enviado com sucesso!!!" );
		}catch(RuntimeException ex){
			ex.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return "home";
	}
	
}
