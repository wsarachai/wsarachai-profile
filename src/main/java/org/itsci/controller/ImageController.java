package org.itsci.controller;

import org.itsci.model.Image;
import org.itsci.service.StudentAttenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
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

    @PostMapping(path = "/images/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
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

    @GetMapping(value = "/images/{ids}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageFromAttendanceById(@PathVariable("ids") String ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }

        Byte[] images = null;
        byte[] outputs = null;

        Image image = studentAttenService.getImageById(Long.parseLong(ids));
        if (image != null) {
            images = image.getImage();
        }

        if (images == null) {
            outputs = loadUnknownImageBytes();
        } else {
            outputs = new byte[images.length];
            for (int i = 0; i < images.length; i++) {
                outputs[i] = images[i];
            }
        }

        return outputs;
    }

    @GetMapping(value = "/images/unknown")
    public @ResponseBody ResponseEntity<byte[]> getUnknownImage() {
        try {
            byte[] bytes = loadUnknownImageBytes();
            if (bytes != null) {
                return ResponseEntity.ok()
                        .contentType(Objects.requireNonNull(MediaType.IMAGE_PNG))
                        .body(bytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(value = "/uploads/attendance/{year}/{month}/{day}/{filename:.+}")
    public @ResponseBody ResponseEntity<byte[]> serveUploadedAttendanceImage(
            @PathVariable("year") String year,
            @PathVariable("month") String month,
            @PathVariable("day") String day,
            @PathVariable("filename") String filename) {
        try {
            String uploadDir = System.getProperty("attendance.upload.dir", "uploads/attendance");
            File file = new File(uploadDir, year + File.separator + month + File.separator + day + File.separator
                    + filename);
            if (!file.exists() || !file.isFile()) {
                // Fallback to classpath unknown image when disk file missing
                byte[] unknown = loadUnknownImageBytes();
                if (unknown != null) {
                    return ResponseEntity.ok()
                            .contentType(Objects.requireNonNull(MediaType.IMAGE_PNG))
                            .body(unknown);
                }
                return ResponseEntity.notFound().build();
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            if (contentType != null) {
                try {
                    mediaType = MediaType.parseMediaType(contentType);
                } catch (Exception ignored) {
                }
            }
            return ResponseEntity.ok().contentType(Objects.requireNonNull(mediaType))
                    .body(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private byte[] loadUnknownImageBytes() {
        try {
            Resource unknownImage = resourceLoader.getResource("classpath:unknow.png");
            if (unknownImage != null && unknownImage.exists()) {
                try (InputStream is = unknownImage.getInputStream()) {
                    return is.readAllBytes();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
