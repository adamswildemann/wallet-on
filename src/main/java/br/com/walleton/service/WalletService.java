package br.com.walleton.service;

import br.com.walleton.api.v1.dto.wallet.OperationRequest;
import br.com.walleton.api.v1.dto.wallet.TransferRequest;
import br.com.walleton.api.v1.dto.wallet.WalletResponse;
import br.com.walleton.domain.model.Wallet;
import br.com.walleton.exception.ResourceNotFoundException;
import br.com.walleton.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public WalletResponse getByUserId(Long userId) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não foi encontrada."));
        return new WalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getBalance());
    }

    @Transactional
    public WalletResponse deposit(Long userId, OperationRequest request) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não foi encontrada."));

        wallet.credit(request.amount());

        return new WalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getBalance());
    }

    @Transactional
    public WalletResponse withdraw(Long userId, OperationRequest request) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não foi encontrada."));

        wallet.debit(request.amount());

        return new WalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getBalance());
    }

    @Transactional
    public void transfer(TransferRequest request) {
        if (request.fromUserId().equals(request.toUserId())) {
            throw new IllegalArgumentException("Transferência para o mesmo usuário não é permitida.");
        }

        Wallet from = repository.findByUserId(request.fromUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet origem não encontrada para o usuário " + request.fromUserId() + "."));
        Wallet to = repository.findByUserId(request.fromUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet destino não encontrada para o usuário " + request.toUserId() + "."));

        from.debit(request.amount());
        to.credit(request.amount());
    }

}
