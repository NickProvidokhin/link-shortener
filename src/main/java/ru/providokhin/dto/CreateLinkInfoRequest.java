package ru.providokhin.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkInfoRequest {

    @NotEmpty(message = "Ссылка не может быть пустой")
    @Pattern(regexp = "^http[s]?://.+\\..+$", message = "url не соответствует паттерну")
    private String link;
    @Future(message = "Дата окончания действия короткой ссылки не может быть в прошлом")
    private LocalDateTime endTime;
    @NotEmpty(message = "Описание не может быть пустым")
    private String description;
    @NotNull(message = "Признак активности не может быть null")
    private Boolean active;
}
