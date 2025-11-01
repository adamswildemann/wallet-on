package br.com.walleton.infrastructure;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionCreatedEvent(Long transactionId,
                                      Long walletId,
                                      String type, // CREDIT ou DEBIT
                                      BigDecimal amount,
                                      String currency, // ex.: "BRL"
                                      Instant occurredAt,
                                      String correlationId) {

}
