package ru.providokhin.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse <T>{

    //по id можем найти ответ в логах быстрее
    private UUID id;
    private T body;
}
