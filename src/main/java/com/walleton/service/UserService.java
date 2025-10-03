package com.walleton.service;

import com.walleton.api.v1.dto.user.UserRequest;
import com.walleton.api.v1.dto.user.UserResponse;
import com.walleton.domain.model.User;
import com.walleton.exception.ResourceNotFoundException;
import com.walleton.mapper.UserMapper;
import com.walleton.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse create(UserRequest request) {
        User entity = mapper.toEntity(request);
        User salvo = repository.save(entity);
        return mapper.toResponse(salvo);
    }

    public UserResponse findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado!"));
        return mapper.toResponse(user);
    }

}
