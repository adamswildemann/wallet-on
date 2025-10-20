package br.com.walleton.api.v1.dto.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OperationRequest(
        @NotNull
        @Positive(message = "Valor deve ser maior que zero.")
        BigDecimal amount,
        String description
) {
}
