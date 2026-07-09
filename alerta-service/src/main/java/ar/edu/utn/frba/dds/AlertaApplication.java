package ar.edu.utn.frba.dds;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"ar.edu.utn.frba.dds"})
@EnableScheduling
public class AlertaApplication {
    public static void main (String[] args){
        new SpringApplicationBuilder(AlertaApplication.class)
            .profiles("dev")
            .run(args);
    }
}