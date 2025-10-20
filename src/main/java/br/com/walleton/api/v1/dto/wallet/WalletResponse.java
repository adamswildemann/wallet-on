package br.com.walleton.api.v1.dto.wallet;

import java.math.BigDecimal;

public record WalletResponse(Long walletId, Long userId, BigDecimal balance) {

}
