package br.com.walleton.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false, precision = 18, scale = 2)
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private BigDecimal balance = BigDecimal.ZERO;

    public void debit(BigDecimal amount) {
        validaAmount(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Valor do saldo é insuficiente");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        validaAmount(amount);
        this.balance = balance.add(amount);
    }

    private void validaAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "O valor não pode ser nulo");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser positivo");
        }
    }

}
