package com.walleton.service;

import com.walleton.api.v1.dto.user.UserRequest;
import com.walleton.api.v1.dto.user.UserResponse;
import com.walleton.domain.model.User;
import com.walleton.mapper.UserMapper;
import com.walleton.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse create(UserRequest request){
        User entity = mapper.toEntity(request);
        User salvo = repository.save(entity);
        return mapper.toResponse(salvo);
    }
}
