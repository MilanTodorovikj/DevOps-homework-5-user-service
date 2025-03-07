package mk.ukim.finki.homework5userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class Homework5UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Homework5UserServiceApplication.class, args);
    }

}
