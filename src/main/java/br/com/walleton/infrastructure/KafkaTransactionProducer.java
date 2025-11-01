package br.com.walleton.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransactionProducer {

    private static final String TOPIC = "transaction-created";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(TransactionCreatedEvent event) {
        String key = String.valueOf(event.walletId());
        kafkaTemplate.send(TOPIC, key, event)
                .whenComplete((r, ex) -> {
                    if (ex != null) {
                        log.error("Falha ao publicar TransactionCreated", ex);
                    } else {
                        log.info("Publicado TransactionCreated topic={} partition={} offset={}",
                                r.getRecordMetadata().topic(),
                                r.getRecordMetadata().partition(),
                                r.getRecordMetadata().offset());
                    }
                });
    }
}
