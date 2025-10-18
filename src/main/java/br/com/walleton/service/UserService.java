package br.com.walleton.service;

import br.com.walleton.api.v1.dto.user.UserRequest;
import br.com.walleton.api.v1.dto.user.UserResponse;
import br.com.walleton.domain.model.User;
import br.com.walleton.domain.model.Wallet;
import br.com.walleton.mapper.UserMapper;
import br.com.walleton.repository.UserRepository;
import br.com.walleton.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final WalletRepository walletRepo;

    @Transactional
    public UserResponse create(UserRequest request) {
        User entity = mapper.toEntity(request);
        User salvo = repository.save(entity);
        Wallet wallet = Wallet.builder()
                .user(salvo)
                .build();
        walletRepo.save(wallet);
        return mapper.toResponse(salvo);
    }

}
