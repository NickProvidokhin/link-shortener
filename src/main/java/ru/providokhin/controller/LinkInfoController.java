package ru.providokhin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;
import ru.providokhin.dto.common.CommonRequest;
import ru.providokhin.dto.common.CommonResponse;
import ru.providokhin.service.LinkInfoService;
import ru.providokhin.validation.ValidUUID;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/link-infos")
public class LinkInfoController {

    private final LinkInfoService linkInfoService;

    @PostMapping
    public CommonResponse<LinkInfoResponse> postCreateLinkInfo(@RequestBody @Valid CommonRequest<CreateLinkInfoRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        LinkInfoResponse linkInfoResponse = linkInfoService.createLinkInfo(request.getBody());

        log.info("Короткая ссылка создана успешно: {}", linkInfoResponse);

        //Сущности помещают в обертку, напрямую с дто не работаем
        return CommonResponse.<LinkInfoResponse>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponse)
                .build();
    }

    @PatchMapping
    public CommonResponse<LinkInfoResponse> patchUpdateLinkInfo(@RequestBody @Valid CommonRequest<UpdateLinkInfoRequest> request) {
        log.info("Поступил запрос на обновление информации о ссылке: {}", request);

        LinkInfoResponse linkInfoResponse = linkInfoService.updateLinkInfo(request.getBody());

        log.info("Информация о ссылке успешно изменена: {}", linkInfoResponse);

        return CommonResponse.<LinkInfoResponse>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public CommonResponse<LinkInfoResponse> deleteLinkInfo(@PathVariable @ValidUUID String id) {
        log.info("Поступил запрос на удаление короткой ссылки: {}", id);

        linkInfoService.deleteLinkByID(UUID.fromString(id));

        log.info("Короткая ссылка успешно удалена: {}", id);

        return CommonResponse.<LinkInfoResponse>builder().
                id(UUID.randomUUID())
                .build();
    }

    @GetMapping
    public CommonResponse<List<LinkInfoResponse>> getLinkInfoByFilter() {
        log.info("Поступил запрос на получение коротких ссылок по фильтру");

        List<LinkInfoResponse> linkInfoResponseList = linkInfoService.findByFilter();

        log.info("Ссылки успешно получены: {}", linkInfoResponseList);

        return CommonResponse.<List<LinkInfoResponse>>builder().
                id(UUID.randomUUID())
                .body(linkInfoResponseList)
                .build();
    }
}
