package ru.providokhin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.exception.NotFoundException;
import ru.providokhin.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShortLinkControllerTest {

    @Test
    public void whenGetByShortLinkThenSuccess() {

        String shortLink = "abcdefgh";
        LinkInfoService linkInfoService = mock(LinkInfoService.class);
        ShortLinkController shortLinkController = new ShortLinkController(linkInfoService);

        LinkInfoResponse response = LinkInfoResponse.builder()
                .id(UUID.randomUUID())
                .shortLink(shortLink)
                .openingCount(0L)
                .link("https://habr.com")
                .endTime(LocalDateTime.now().plusDays(1))
                .active(true)
                .build();
        when(linkInfoService.getByShortLink(shortLink)).thenReturn(response);

        ResponseEntity<String> responseEntity = shortLinkController.getByShortLink(shortLink);

        verify(linkInfoService, times(1)).getByShortLink(shortLink);
        assertEquals(HttpStatus.TEMPORARY_REDIRECT, responseEntity.getStatusCode());
        assertEquals(response.getLink(), responseEntity.getHeaders().getFirst(HttpHeaders.LOCATION));
    }

    @Test
    public void whenGetByShortLinkThenFailed() {

        String shortLink = "testtest";
        LinkInfoService linkInfoService = mock(LinkInfoService.class);

        ShortLinkController shortLinkController = new ShortLinkController(linkInfoService);
        when(linkInfoService.getByShortLink(shortLink)).thenThrow(new NotFoundException("Не удалось найти по короткой ссылке: "));

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> shortLinkController.getByShortLink(shortLink));
        verify(linkInfoService, times(1)).getByShortLink(shortLink);
    }

}