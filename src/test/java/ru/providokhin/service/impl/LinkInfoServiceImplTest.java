package ru.providokhin.service.impl;

import org.junit.jupiter.api.Test;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.exception.NotFoundException;
import ru.providokhin.repository.impl.LinkInfoRepositoryImpl;
import ru.providokhin.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.providokhin.util.Constants.LINK_LENGTH;

public class LinkInfoServiceImplTest {

    LinkInfoService linkInfoService = new LinkInfoServiceImpl(new LinkInfoRepositoryImpl());

    @Test
    public void testGenerateShortLink() {

        String link = "https://youtube.com";
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(12);
        String description = "Start page of service Youtube";
        boolean activate = true;
        CreateLinkInfoRequest createLinkInfoRequest = new CreateLinkInfoRequest(link, localDateTime, description, activate);

        LinkInfoResponse linkInfoResponse = linkInfoService.createLinkInfo(createLinkInfoRequest);
        System.out.println("Short link: " + linkInfoResponse);

    }

    @Test
    void when_createShortLink_then_success() {
        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .build();

        LinkInfoResponse response = linkInfoService.createLinkInfo(request);
        assertEquals(LINK_LENGTH, response.getShortLink().length());

        LinkInfoResponse byShortLink = linkInfoService.getByShortLink((response.getShortLink()));
        assertNotNull(byShortLink);
    }

    @Test
    void when_FindByFilter_then_success() {
        CreateLinkInfoRequest firstRequest = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .description("google start page")
                .endTime(LocalDateTime.now().plusDays(2))
                .build();
        CreateLinkInfoRequest secondRequest = CreateLinkInfoRequest.builder()
                .link("http://vk.com")
                .description("vk start page")
                .endTime(LocalDateTime.now().plusDays(1))
                .build();
        CreateLinkInfoRequest thirdRequest = CreateLinkInfoRequest.builder()
                .link("http://ya.ru")
                .description("yandex start page")
                .endTime(LocalDateTime.now().plusDays(3))
                .build();

        linkInfoService.createLinkInfo(firstRequest);
        linkInfoService.createLinkInfo(secondRequest);
        linkInfoService.createLinkInfo(thirdRequest);

        List<LinkInfoResponse> listByFindAll = linkInfoService.findByFilter();
        assertEquals(3, listByFindAll.size());
    }

    @Test
    void when_getByShortLink_then_fail() {
        String searchShortLink = "test";

        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .description("google start page")
                .endTime(LocalDateTime.now().plusDays(2))
                .build();

        linkInfoService.createLinkInfo(request);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> linkInfoService.getByShortLink(searchShortLink));

        assertEquals("Не удалось найти по короткой ссылке: test", thrown.getMessage());
    }
}
