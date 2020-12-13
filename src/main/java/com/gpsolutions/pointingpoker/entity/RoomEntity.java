package com.gpsolutions.pointingpoker.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomEntity extends AbstractEntity {
    @NotBlank
    private String name;
    @NotBlank
    private String adminId;
    @NotNull
    private List<String> userIds;
    @NotNull
    private List<Double> pointValues;
    @NotNull
    private String storyDescription;
}
