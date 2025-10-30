package br.com.walleton.api.v1.controller;

import br.com.walleton.api.v1.dto.wallet.OperationRequest;
import br.com.walleton.api.v1.dto.wallet.TransferRequest;
import br.com.walleton.api.v1.dto.wallet.WalletResponse;
import br.com.walleton.domain.model.Transaction;
import br.com.walleton.exception.ResourceNotFoundException;
import br.com.walleton.repository.TransactionRepository;
import br.com.walleton.repository.WalletRepository;
import br.com.walleton.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController {

    private final WalletService walletService;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public WalletController(WalletService walletService, TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WalletResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getByUserId(userId));
    }

    @PostMapping(value = "/{userId}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResponse> deposit(@PathVariable Long userId, @Valid @RequestBody OperationRequest request) {
        WalletResponse response = walletService.deposit(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/{userId}/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResponse> withdraw(@PathVariable Long userId, @Valid @RequestBody OperationRequest request) {
        WalletResponse response = walletService.withdraw(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest request) {
        walletService.transfer(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{userId}/transactions")
    public ResponseEntity<Page<Transaction>> listTransactions(@PathVariable Long userId, Pageable pageable) {
        Long walletId = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não encontrada."))
                .getId();

        Page<Transaction> page = transactionRepository.findByWallet_Id(walletId, pageable);
        return ResponseEntity.ok(page);
    }

}
