package com.gpsolutions.pointingpoker.dto;

import com.gpsolutions.pointingpoker.util.RegExpUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @Pattern(regexp = RegExpUtil.EMAIL_REGEXP, message = "Invalid email format")
    private String email;
    @Pattern(regexp = RegExpUtil.PASSWORD_REGEXP, message = "Invalid password strength")
    private String password;
    @NotBlank
    private String name;
}
