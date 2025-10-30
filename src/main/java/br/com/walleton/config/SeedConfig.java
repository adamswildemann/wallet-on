package br.com.walleton.config;

import br.com.walleton.api.v1.dto.wallet.OperationRequest;
import br.com.walleton.api.v1.dto.wallet.TransferRequest;
import br.com.walleton.domain.model.User;
import br.com.walleton.domain.model.Wallet;
import br.com.walleton.repository.UserRepository;
import br.com.walleton.repository.WalletRepository;
import br.com.walleton.service.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SeedConfig {

    private static final int NEW_USERS = 4;

    // @Bean
    CommandLineRunner runner(
            WalletService walletService,
            UserRepository userRepo,
            WalletRepository walletRepo
    ) {
        return args -> {
            // Cria novos usuários (se não existirem)
            List<Long> createdIds = new ArrayList<>();
            for (int i = 1; i <= NEW_USERS; i++) {
                String email = "seed.user" + i + "@walleton.com";
                String cpf = String.format("%011d", 90000000000L + i);
                String phone = "5198888" + String.format("%04d", i);

                boolean exists = userRepo.existsByEmail(email); // ou findByEmail(email).isPresent()
                if (!exists) {
                    User u = User.builder()
                            .firstName("Seed")
                            .lastName("User" + i)
                            .email(email)
                            .cpf(cpf)
                            .phoneNumber(phone)
                            .build();
                    u = userRepo.save(u);

                    // Cria carteira se não existir
                    if (walletRepo.findByUserId(u.getId()).isEmpty()) {
                        Wallet w = Wallet.builder()
                                .user(u)
                                .balance(BigDecimal.ZERO)
                                .build();
                        walletRepo.save(w);
                    }
                    createdIds.add(u.getId());
                }
            }

            // Todos os usuários (existentes + criados)
            List<Long> userIds = userRepo.findAll().stream()
                    .map(User::getId)
                    .toList();

            // Garante wallet para todos (se faltar)
            for (Long uid : userIds) {
                walletRepo.findByUserId(uid).orElseGet(() -> {
                    User u = userRepo.findById(uid).orElseThrow();
                    Wallet w = Wallet.builder()
                            .user(u)
                            .balance(BigDecimal.ZERO)
                            .build();
                    return walletRepo.save(w);
                });
            }

            // Mais de 25 transações por user (15 depósitos + 10 saques)
            for (Long uid : userIds) {
                for (int v : new int[]{10, 12, 15, 18, 20, 25, 30, 35, 40, 45, 50, 55, 60, 70, 80}) {
                    walletService.deposit(uid, new OperationRequest(BigDecimal.valueOf((long) v), "Depósito"));
                }
                for (int v : new int[]{5, 7, 9, 11, 13, 17, 19, 21, 23, 27}) {
                    walletService.withdraw(uid, new OperationRequest(BigDecimal.valueOf((long) v), "Saque"));
                }
            }

            // Transferências entre pares
            for (int i = 0; i + 1 < userIds.size(); i += 2) {
                Long from = userIds.get(i), to = userIds.get(i + 1);
                for (int v : new int[]{7, 13, 21, 9, 30}) {
                    walletService.transfer(new TransferRequest(
                            from, to, BigDecimal.valueOf((long) v), "Transferência"
                    ));
                }
            }

            System.out.println("[seed] concluído. Usuários criados: " + createdIds);
        };
    }
}
