package org.itsci.controller;

import org.itsci.model.Attendance;
import org.itsci.service.StudentAttenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/pub")
public class ImageController {

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

        if ("lec".equals(imageId)) {
            image = attendance.getStudentImage();
        } else {
            image = attendance.getCodeImage();
        }

        byte [] outputs = new byte[image.length];
        for (int i = 0; i < image.length; i++) {
            outputs[i] = image[i].byteValue();
        }

        return outputs;
    }
}
