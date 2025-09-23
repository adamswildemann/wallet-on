package com.walleton.mapper;

import com.walleton.domain.model.User;
import com.walleton.dto.user.UserRequest;
import com.walleton.dto.user.UserResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest dto);

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    void updateEntityFromDto(UserRequest dto, @MappingTarget User entity);

}
