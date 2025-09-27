CREATE TABLE `wallets`
(
    `id`         BIGINT         NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT         NOT NULL,
    `balance`    DECIMAL(18, 2) NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP      NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),

    UNIQUE KEY `uk_wallets_user_id` (`user_id`),
    CONSTRAINT `fk_wallets_user`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;