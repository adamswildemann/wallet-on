package com.walleton.api.v1.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotBlank @Size(max = 80) String firstName,

                          @NotBlank @Size(max = 80) String lastName,

                          @NotBlank @Email @Size(max = 60) String email,

                          @NotBlank @Size(min = 11, max = 14) String cpf,

                          @NotBlank @Size(max = 20) String phoneNumber

) {
}
