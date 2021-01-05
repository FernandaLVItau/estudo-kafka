package br.com.pessoal.estudokafka.consumer;

import br.com.pessoal.estudokafka.EventoDeTransacao;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/*
    Ação ao receber/listener do topico
 */

@Component
public class ConsumidorDeTransacao {

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvir(EventoDeTransacao evento) {
        System.out.println("Recebido evento: "+evento);
    }
}