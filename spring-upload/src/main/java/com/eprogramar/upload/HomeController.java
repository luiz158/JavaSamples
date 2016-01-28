package com.eprogramar.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

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
}
