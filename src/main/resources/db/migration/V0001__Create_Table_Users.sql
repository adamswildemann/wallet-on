CREATE TABLE IF NOT EXISTS `users` (
                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                       `first_name` VARCHAR(80)  NOT NULL,
    `last_name`  VARCHAR(80)  NOT NULL,
    `email`      VARCHAR(60)  NOT NULL,
    `cpf`        VARCHAR(14)  NOT NULL,
    `phone`      VARCHAR(20)  NOT NULL,          -- na entity está @Column(name="phone")
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_email` (`email`),
    UNIQUE KEY `uk_users_cpf`   (`cpf`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;