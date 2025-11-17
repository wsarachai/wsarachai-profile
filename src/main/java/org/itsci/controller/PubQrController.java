package org.itsci.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Controller
@RequestMapping("/system")
public class PubQrController {

  @GetMapping("/qr")
  public String qrForm(@RequestParam(name = "text", required = false) String text,
      @RequestParam(name = "size", required = false, defaultValue = "300") int size,
      Model model) {
    model.addAttribute("text", text);
    model.addAttribute("size", size);

    if (text != null && !text.trim().isEmpty()) {
      try {
        String dataUri = generateQrDataUri(text, size);
        model.addAttribute("qrcodeDataUri", dataUri);
      } catch (Exception e) {
        model.addAttribute("error", "Failed to generate QR: " + e.getMessage());
      }
    }

    return "system/qr";
  }

  private String generateQrDataUri(String text, int size) throws WriterException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
      byte[] bytes = baos.toByteArray();
      String base64 = Base64.getEncoder().encodeToString(bytes);
      return "data:image/png;base64," + base64;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
