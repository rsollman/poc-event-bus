package org.codeit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.codeit.domain.CreateOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Value(value = "${spring.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public RetryTemplate kafkaRetry(){
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100*1000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }

    /*@Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> createOfferKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer factoryConfigurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, CreateOffer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factoryConfigurer.configure(factory, kafkaConsumerFactory);

        factory.setRetryTemplate(kafkaRetry());
        factory.setConsumerFactory(createOfferConsumerFactory());
        factory.setRecoveryCallback(retryContext -> {
            ConsumerRecord consumerRecord = (ConsumerRecord) retryContext.getAttribute("record");
            logger.info("Recovery is called for message {} ", consumerRecord.value());
            return Optional.empty();
        });
        return factory;
    }*/

    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> usersKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory("groupd_id");
    }


    public ConsumerFactory<String, CreateOffer> createOfferConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "history_group");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CreateOffer.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateOffer> createOfferKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CreateOffer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createOfferConsumerFactory());
        return factory;
    }


}
