package br.com.walleton.repository;

import br.com.walleton.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByWallet_Id(Long walletId, Pageable pageable);

}
