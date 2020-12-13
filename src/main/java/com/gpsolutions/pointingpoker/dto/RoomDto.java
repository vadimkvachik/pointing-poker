package com.gpsolutions.pointingpoker.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoomDto {
    private String id;
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
