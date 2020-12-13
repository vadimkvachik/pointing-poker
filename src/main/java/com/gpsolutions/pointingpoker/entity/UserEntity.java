package com.gpsolutions.pointingpoker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gpsolutions.pointingpoker.enums.UserRoleEnum;
import com.gpsolutions.pointingpoker.util.RegExpUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity extends AbstractEntity {

    @Indexed(unique = true)
    @Pattern(regexp = RegExpUtil.EMAIL_REGEXP, message = "Invalid email format")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private UserRoleEnum role;
    private boolean active;
}
