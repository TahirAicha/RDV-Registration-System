package src.main.java.web;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleMailController {
    @Autowired
    private JavaMailSender sender;

    @RequestMapping("/sendMail")
    public String sendMail() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("aichatahiir@gmail.com");
            helper.setText("Bonjour,");
            helper.setSubject("Confirmation de l'adresse email");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Erreur d'envoie du mail ..";
        }
        sender.send(message);
        return "Mail envoyé avec succes!";
    }
}