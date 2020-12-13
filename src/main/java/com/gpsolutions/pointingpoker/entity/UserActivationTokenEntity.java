package com.gpsolutions.pointingpoker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserActivationTokenEntity extends AbstractEntity {
    @NotBlank
    private String token;
    @NotBlank
    private String email;
    @NotNull
    private LocalDate createdOn;
}
