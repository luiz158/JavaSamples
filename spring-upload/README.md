# Exemplo de upload de arquivos   
-------

**Dependencias**   
```xml
<!-- Upload -->
<dependency>
	<groupId>commons-fileupload</groupId>
	<artifactId>commons-fileupload</artifactId>
	<version>1.2.1</version>
</dependency>
<dependency>
	<groupId>commons-io</groupId>
	<artifactId>commons-io</artifactId>
	<version>1.4</version>
</dependency>
```    

**Configurar Bean multipartResolver**   
```xml
<beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
 	<!-- one of the properties available; the maximum file size in bytes -->
 	<beans:property name="maxUploadSize" value="2000000" />
</beans:bean>
```   

**Controller**   
```java
@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
public String upload(@RequestParam("file") MultipartFile file, Model model) {
	
	String name = null;
	if (!file.isEmpty()) {
		
			name = file.getOriginalFilename();
			
        // Creating the directory to store file
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists())
            dir.mkdirs();

        // Create the file on server
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream( serverFile ));
            stream.write(bytes);
            stream.close();
            model.addAttribute("message", "You successfully uploaded " + name + "!");
        } catch (Exception e) {
            return "You failed to upload " + name + " => " + e.getMessage();
        }
        
    } else {
    	model.addAttribute("message", "You failed to upload " + name + " because the file was empty.");
    }		
	
	return "home";
}
```   

**Form HTML**   
```html
<p>${ message }</p>

<form action="${ pageContext.request.contextPath }/uploadFile" method="post" enctype="multipart/form-data">
	<p>
 		<label for="file">Arquivo para fazer upload:</label><br/>
 		<input type="file" name="file" /><br/>
 		<input type="submit" name="submit" value="Upload" /><br/>
	</p>
</form>
```   
