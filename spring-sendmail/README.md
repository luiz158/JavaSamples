# Envio de Email Spring MVC   
---------------------------

> Exemplo de envio de email com Copias, anexo e corpo em HTML   

**Dependencias**   
```xml
<!-- Email -->
<dependency>
	<groupId>javax.mail</groupId>
	<artifactId>mail</artifactId>
	<version>1.4.7</version>
</dependency>

```   

**Configuração do Bean mailSender**   
```xml
<beans:bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
	<beans:property name="host" value="smtp.gmail.com" />
	<beans:property name="port" value="587" />
	<beans:property name="username" value="EMAIL_ORIGEM" />
	<beans:property name="password" value="SENHA_EMAIL" />
	<beans:property name="javaMailProperties">
	    <beans:props>
	        <beans:prop key="mail.transport.protocol">smtp</beans:prop>
	        <beans:prop key="mail.smtp.auth">true</beans:prop>
	        <beans:prop key="mail.smtp.starttls.enable">true</beans:prop>
	    </beans:props>
	</beans:property>
</beans:bean>
```   

**Controller**
```java
try{
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
```   



