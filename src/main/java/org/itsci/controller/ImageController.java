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
            value = "/images/{attenId}/{imageId}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageFromAttendanceById(Model model, @PathVariable("attenId") String attenId, @PathVariable("imageId") String imageId) {
        Attendance attendance = studentAttenService.findAttendanceById(Long.valueOf(attenId));
        if (attendance == null) {
            return null;
        }

        if (imageId == null || imageId.isEmpty()) {
            return null;
        }

        Byte[] image = null;
        byte [] outputs = null;

        if ("img1".equals(imageId)) {
            Image image1 = studentAttenService.getImageById(attendance.getImage1_id());
            if (image1 != null) {
                image = image1.getImage();
            }
        }
        if ("img2".equals(imageId)) {
            Image image2 = studentAttenService.getImageById(attendance.getImage2_id());
            if (image2 != null) {
                image = image2.getImage();
            }
        }

        if (image == null) {
            Resource unknownImage = resourceLoader.getResource("classpath:unknow.png");
            try {
                File file = unknownImage.getFile();
                outputs = Files.readAllBytes(file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            outputs = new byte[image.length];
            for (int i = 0; i < image.length; i++) {
                outputs[i] = image[i].byteValue();
            }
        }

        return outputs;
    }
}
