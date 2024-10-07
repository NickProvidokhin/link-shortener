package ru.providokhin.service.impl;

import org.junit.jupiter.api.Test;
import ru.providokhin.dto.CreateLinkInfoRequest;

import java.time.LocalDateTime;

public class LinkInfoServiceImplTest {
    @Test
    public void testGenerateShortLink() {

        String link = "https://youtube.com";
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(12);
        String description = "Start page of service Youtube";
        Boolean activate = true;
        CreateLinkInfoRequest createLinkInfoRequest = new CreateLinkInfoRequest(link, localDateTime, description, activate);
        LinkInfoServiceImpl linkInfoService = new LinkInfoServiceImpl();

        String shortLink = linkInfoService.createShortLink(createLinkInfoRequest);
        System.out.println("Short link: " + shortLink);

    }
}
