package com.PFE.RH.Controllers;

import com.PFE.RH.Services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.mail.MessagingException;

import java.util.Map;

@RestController
public class MailingController {

    @Autowired
    private MailingService mailingService;

    @PostMapping("/send-html-email")
    public ResponseEntity<?> sendHtmlEmail(@RequestBody Map<String, String> request) {
        String to = request.get("to");
        String subject = request.get("subject");
        String htmlContent = request.get("htmlContent");

        try {
            mailingService.sendHtmlEmail(to, subject, htmlContent);
            return ResponseEntity.ok("HTML Email sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send HTML email");
        }
    }
}
