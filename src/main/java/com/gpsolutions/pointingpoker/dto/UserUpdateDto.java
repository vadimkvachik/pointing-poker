package com.gpsolutions.pointingpoker.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    private String password;
}
