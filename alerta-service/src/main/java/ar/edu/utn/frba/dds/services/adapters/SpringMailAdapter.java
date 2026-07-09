package ar.edu.utn.frba.dds.adapters;

import ar.edu.utn.frba.dds.domain.Alerta;
import ar.edu.utn.frba.dds.domain.NotificadorAlerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SpringMailAdapter implements NotificadorAlerta{
    private final JavaMailSender mailSender;
    private final String[] destinatarios = {
        "admin@clima.com",
        "emergencias@clima.com",
        "meteorologia@clima.com"
    };
    
    public SpringMailAdapter(@Autowired(required = false) JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override 
    public void notificar(Alerta alerta){
        if(mailSender == null) {
            System.out.println("\n[SMTP MOCK] Enviando correo de alerta...");
            for(String dest : destinatarios){
                System.out.printf(">> Para %s\n>> Asunto: ALERTA METEOROLÓGICA\n>> Mensaje: %s\n\n", dest, alerta.getMensaje());
            }
            return;
        }
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("climaler@clima.com");
            mail.setTo(destinatarios);
            mail.setSubject("ALETA METEOROLÓGICA");
            mail.setText(alerta.getMensaje());

            mailSender.send(mail);
            System.out.println("Correos de alerta enviados exitosamente");
        } catch (Exception e){
            System.err.println("Error al enviar correos de alerta: " + e.getMessage());
        }
    }
}
