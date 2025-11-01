package br.com.walleton.service;

import br.com.walleton.api.v1.dto.wallet.OperationRequest;
import br.com.walleton.api.v1.dto.wallet.TransferRequest;
import br.com.walleton.api.v1.dto.wallet.WalletResponse;
import br.com.walleton.domain.model.Transaction;
import br.com.walleton.domain.model.Wallet;
import br.com.walleton.domain.model.enums.TransactionType;
import br.com.walleton.exception.ResourceNotFoundException;
import br.com.walleton.infrastructure.KafkaTransactionProducer;
import br.com.walleton.infrastructure.TransactionCreatedEvent;
import br.com.walleton.repository.TransactionRepository;
import br.com.walleton.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final KafkaTransactionProducer kafkaProducer;
    private static final String DEFAULT_CURRENCY = "BRL";

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository, KafkaTransactionProducer kafkaProducer) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public WalletResponse getByUserId(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não foi encontrada."));
        return new WalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getBalance());
    }

    @Transactional
    public WalletResponse deposit(Long userId, OperationRequest request) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não foi encontrada."));

        wallet.credit(request.amount());
        registrarTransacao(wallet, TransactionType.DEPOSIT, request.amount(), "Depósito realizado!");

        return new WalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getBalance());
    }

    @Transactional
    public WalletResponse withdraw(Long userId, OperationRequest request) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Carteira do usuário " + userId + " não foi encontrada."));

        wallet.debit(request.amount());

        registrarTransacao(wallet, TransactionType.WITHDRAW, request.amount(), "Saque realizado!");

        return new WalletResponse(wallet.getId(), wallet.getUser().getId(), wallet.getBalance());
    }

    @Transactional
    public void transfer(TransferRequest request) {
        if (request.fromUserId().equals(request.toUserId())) {
            throw new IllegalArgumentException("Transferência para o mesmo usuário não é permitida.");
        }

        Wallet from = walletRepository.findByUserId(request.fromUserId()).orElseThrow(() -> new ResourceNotFoundException("Carteira origem não encontrada para o usuário " + request.fromUserId() + "."));
        Wallet to = walletRepository.findByUserId(request.toUserId()).orElseThrow(() -> new ResourceNotFoundException("Carteira destino não encontrada para o usuário " + request.toUserId() + "."));

        from.debit(request.amount());
        to.credit(request.amount());

        registrarTransacao(from, TransactionType.TRANSFER_OUT, request.amount(), "Transferência enviada para " + request.toUserId());
        registrarTransacao(to, TransactionType.TRANSFER_IN, request.amount(), "Transferência recebida de " + request.fromUserId());
    }

    private Transaction registrarTransacao(Wallet wallet, TransactionType type, BigDecimal amount, String description) {
        Transaction saved = transactionRepository.save(
                Transaction.builder().wallet(wallet).type(type).amount(amount).description(description).build());

        publicarEvento(saved, wallet, type);
        return saved;
    }

    private void publicarEvento(Transaction saved, Wallet wallet, TransactionType type) {
        var event = new TransactionCreatedEvent(
                saved.getId(), wallet.getId(), type.name(), saved.getAmount(), DEFAULT_CURRENCY, Instant.now(), null);
        kafkaProducer.publish(event);
    }

}
