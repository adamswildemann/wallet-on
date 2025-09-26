CREATE TABLE IF NOT EXISTS users (
                                     id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     first_name    VARCHAR(80)  NOT NULL,
    last_name     VARCHAR(80)  NOT NULL,
    email         VARCHAR(120) NOT NULL,
    cpf           VARCHAR(14)  NOT NULL,
    phone_number  VARCHAR(20)  NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_email        UNIQUE (email),
    CONSTRAINT uk_users_cpf          UNIQUE (cpf),
    CONSTRAINT uk_users_phone_number UNIQUE (phone_number)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
CREATE INDEX IF NOT EXISTS idx_users_cpf   ON users (cpf);
