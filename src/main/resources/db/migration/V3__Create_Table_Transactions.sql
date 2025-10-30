CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_transactions_wallet
        FOREIGN KEY (wallet_id) REFERENCES wallets (id),
    INDEX idx_transactions_wallet (wallet_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;