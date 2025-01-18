package ru.providokhin.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.providokhin.validation.ValidUUID;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkInfoRequest {

    @ValidUUID
    private String id;
    @Pattern(regexp = "^http[s]?://.+\\..+$", message = "url не соответствует паттерну")
    private String link;
    @Future(message = "Дата окончания действия короткой ссылки не может быть в прошлом")
    private LocalDateTime endTime;
    private String description;
    private Boolean active;
}
