package ru.providokhin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkInfoRequest {
    private UUID id;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean activate;
}
