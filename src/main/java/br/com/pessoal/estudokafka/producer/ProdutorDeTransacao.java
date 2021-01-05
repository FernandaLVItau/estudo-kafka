package br.com.pessoal.estudokafka.producer;

import br.com.pessoal.estudokafka.EventoDeTransacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.math.BigDecimal;

@Component
public class ProdutorDeTransacao {

    @Autowired
    private KafkaTemplate<String, EventoDeTransacao> kafkaTemplate;

    @Value("${spring.kafka.topic.transactions}")
    private String topicName;
    private int numTeste;


    public void enviarMensagem(EventoDeTransacao evento) {

        ListenableFuture<SendResult<String, EventoDeTransacao>> future =
                kafkaTemplate.send(topicName, evento);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, EventoDeTransacao> result) {
                System.out.println("Enviado evento=[" + evento +
                        "] com offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Evento nao enviado=["
                        + evento + "] mensagem da excecao: " + ex.getMessage());
            }
        });
    }

    @Scheduled(fixedRate = 10000)
    public void produzir() {

        numTeste ++;
        String id = "TESTE_" + numTeste;
        BigDecimal valor = new BigDecimal("10.0");

        EventoDeTransacao evento = new EventoDeTransacao(id, valor);

        enviarMensagem(evento);
    }

}
