package br.com.walleton.api.v1.controller;

import br.com.walleton.api.v1.dto.wallet.OperationRequest;
import br.com.walleton.api.v1.dto.wallet.WalletResponse;
import br.com.walleton.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WalletResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PostMapping(value = "/{userId}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResponse> deposit(@PathVariable Long userId, @Valid @RequestBody OperationRequest request) {
        WalletResponse response = service.deposit(userId, request);
        return ResponseEntity.ok(response);
    }

}
