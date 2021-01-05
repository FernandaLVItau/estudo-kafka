package br.com.pessoal.estudokafka.consumer;

import br.com.pessoal.estudokafka.EventoDeTransacao;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/*
 *  Configurações kafka para o consumidor
 *
 *  Com as configurações no application.properties,
 *  esta classe não é necessária. Funcionará com ou sem ela.
 */

@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    /**
     * Configurar as propriedades do consumidor kafka
     */
    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "minha-aplicacao");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return properties;
    }

    /**
     * Configurar o consumidor do Kafka
     *  - definir o desserializador da chave (StringDeserializer)
     *  - definir o desserializador do evento/mensageem (JsonDeserializer da classe EventoDeTransacao)
     *  - definir as configurações (consumerConfigurations)
     */
    @Bean
    public ConsumerFactory<String, EventoDeTransacao> transacaoConsumerFactory() {
        StringDeserializer stringDeserializer = new StringDeserializer();
        JsonDeserializer<EventoDeTransacao> jsonDeserialize = new JsonDeserializer(EventoDeTransacao.class, false);

        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), stringDeserializer, jsonDeserialize);
    }


    /**
     * Configurar o listener - como será tratado os eventos recebidos no listener
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventoDeTransacao> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EventoDeTransacao> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transacaoConsumerFactory());

        return factory;
    }
}
