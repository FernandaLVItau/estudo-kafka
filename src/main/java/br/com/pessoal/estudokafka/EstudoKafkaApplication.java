package br.com.pessoal.estudokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EstudoKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudoKafkaApplication.class, args);
	}

}
