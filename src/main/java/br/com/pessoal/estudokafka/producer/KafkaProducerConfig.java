package br.com.pessoal.estudokafka.producer;

import br.com.pessoal.estudokafka.EventoDeTransacao;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/*
 *  Configurações kafka para o produtor
 *
 *  Com as configurações no application.properties,
 *  esta classe não é necessária. Funcionará com ou sem ela.
 */


@Configuration
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * Configurar o produtor do Kafka
     * Configurar as propriedades do consumidor kafka
     */
    @Bean
    public ProducerFactory<String, EventoDeTransacao> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    /**
     * Configurar o template
     * KafkaTemplate envolve uma instância do Produtor
     * e fornece métodos convenientes para enviar mensagens aos tópicos do Kafka.
     */
    @Bean
    public KafkaTemplate<String, EventoDeTransacao> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
