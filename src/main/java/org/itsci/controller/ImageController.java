package org.itsci.controller;

import org.itsci.model.Attendance;
import org.itsci.model.Image;
import org.itsci.service.StudentAttenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Controller
@RequestMapping("/pub")
public class ImageController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private StudentAttenService studentAttenService;

    @GetMapping("/images/new")
    public String newImage(Model model) {
        return "upload_form";
    }

    @PostMapping(path="/images/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String uploadImage(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "upload_form";
    }

    @GetMapping(
            value = "/images/{ids}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageFromAttendanceById(@PathVariable("ids") String ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }

        Byte[] images = null;
        byte [] outputs = null;

        Image image = studentAttenService.getImageById(Long.parseLong(ids));
        if (image != null) {
            images = image.getImage();
        }

        if (images == null) {
            Resource unknownImage = resourceLoader.getResource("classpath:unknow.png");
            try {
                File file = unknownImage.getFile();
                outputs = Files.readAllBytes(file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            outputs = new byte[images.length];
            for (int i = 0; i < images.length; i++) {
                outputs[i] = images[i];
            }
        }

        return outputs;
    }
}
