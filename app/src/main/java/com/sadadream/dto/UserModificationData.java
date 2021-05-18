package com.sadadream.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class UserModificationData {
    @NotBlank
    @Mapping("name")
    private final String name;

    @NotBlank
    @Size(min = 4, max = 1024)
    @Mapping("password")
    private final String password;
}
