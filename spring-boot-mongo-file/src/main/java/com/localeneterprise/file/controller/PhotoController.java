package com.localeneterprise.file.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.localeneterprise.file.dao.PhotoDAO;
import com.localeneterprise.file.model.Photo;
import com.localeneterprise.file.service.PhotoService;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/photos/{id}")
    public String getPhoto(@PathVariable String id, Model model) {
        Photo photo = photoService.getPhoto(id);
        model.addAttribute("title", photo.getTitle());
        model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return "photos";
    }

    @GetMapping("/photos/upload")
    public String uploadPhoto(Model model) {
        model.addAttribute("message", "hello");
        return "uploadPhoto";
    }

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image, Model model) throws IOException {
        String id = photoService.addPhoto(title, image);
        return "redirect:/photos/" + id;
    }
    
    @GetMapping("/photos/all")
    public String getAllPhotos(Model model){
    	
    	model.addAttribute("photos", photoService.getAllPhotos()
			    	 .stream()
			    	 .map(photo->{
			    		 PhotoDAO photoDAO	= new PhotoDAO();
			    		 photoDAO.setTitle(photo.getTitle());
			    		 photoDAO.setImage( Base64.getEncoder().encodeToString(photo.getImage().getData()));
			    		 return photoDAO;
			    	 }).collect(Collectors.toList()));
    	
    	return "photoList";
			    	
    	
    }
}