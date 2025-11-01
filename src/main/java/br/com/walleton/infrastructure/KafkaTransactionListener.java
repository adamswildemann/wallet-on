package br.com.walleton.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaTransactionListener {

    @KafkaListener(topics = "transaction-created", groupId = "walleton-consumer")
    public void onMessage(@Payload TransactionCreatedEvent event) {
        log.info("[CONSUMER] TransactionCreated -> tx={} wallet={} amount={} {} type={}",
                event.transactionId(), event.walletId(), event.amount(), event.currency(), event.type());
    }

}
