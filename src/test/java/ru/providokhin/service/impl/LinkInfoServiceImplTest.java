package ru.providokhin.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;
import ru.providokhin.exception.NotFoundException;
import ru.providokhin.property.LinkShortenerProperty;
import ru.providokhin.repository.LinkInfoRepository;
import ru.providokhin.repository.impl.LinkInfoRepositoryImpl;
import ru.providokhin.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LinkInfoServiceImplTest {

    @Autowired
    private LinkInfoService linkInfoService;

    @Autowired
    private LinkShortenerProperty linkShortenerProperty;

    @Test
    public void whenGenerateShortLinkThenSuccess() {
        String link = "https://youtube.com";
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(12);
        String description = "Start page of service Youtube";
        boolean active = true;
        CreateLinkInfoRequest createLinkInfoRequest = new CreateLinkInfoRequest(link, localDateTime, description, active);

        LinkInfoResponse linkInfoResponse = linkInfoService.createLinkInfo(createLinkInfoRequest);

        assertNotNull(linkInfoResponse);
    }

    @Test
    void whenCreateShortLinkThenSuccess() {
        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .build();

        LinkInfoResponse response = linkInfoService.createLinkInfo(request);
        assertEquals(linkShortenerProperty.getShortLinkLength(), response.getShortLink().length());

        LinkInfoResponse byShortLink = linkInfoService.getByShortLink((response.getShortLink()));
        assertNotNull(byShortLink);
    }

    @Test
    void whenFindByFilterThenSuccess() {

        LinkInfoService linkInfoServiceLoggingProxy = initForTests();

        //LinkInfoService linkInfoServiceLoggingProxy = new LinkInfoServiceLoggingProxy(linkShortenerProperty);
        //Если сделать так то дублируется лог

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

        linkInfoServiceLoggingProxy.createLinkInfo(firstRequest);
        linkInfoServiceLoggingProxy.createLinkInfo(secondRequest);
        linkInfoServiceLoggingProxy.createLinkInfo(thirdRequest);

        List<LinkInfoResponse> listByFindAll = linkInfoServiceLoggingProxy.findByFilter();
        assertEquals(3, listByFindAll.size());
    }

    @Test
    void whenGetByShortLinkThenFail() {
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

    @Test
    void whenDeleteLinkByIDThenSuccess() {
        LinkInfoService linkInfoServiceLoggingProxy = initForTests();

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

        LinkInfoResponse firstLinkInfoResponse = linkInfoServiceLoggingProxy.createLinkInfo(firstRequest);
        linkInfoServiceLoggingProxy.createLinkInfo(secondRequest);

        linkInfoServiceLoggingProxy.deleteLinkByID(firstLinkInfoResponse.getId());
        List<LinkInfoResponse> listByFindAll = linkInfoServiceLoggingProxy.findByFilter();
        assertEquals(1, listByFindAll.size());
    }

    @Test
    void whenDeleteLinkByIDThenFailed() {
        LinkInfoService linkInfoServiceLoggingProxy = initForTests();

        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .description("google start page")
                .endTime(LocalDateTime.now().plusDays(2))
                .build();
        LinkInfoResponse firstLinkInfoResponse = linkInfoServiceLoggingProxy.createLinkInfo(request);


        linkInfoServiceLoggingProxy.deleteLinkByID(UUID.randomUUID());
        List<LinkInfoResponse> listByFindAll = linkInfoServiceLoggingProxy.findByFilter();
        assertEquals(1, listByFindAll.size());
    }

    @Test
    void whenUpdateLinkInfoThenSuccess() {
        String newLink = "ya.ru";
        LocalDateTime newEndTime = LocalDateTime.now().plusDays(1);
        String newDescription = "update google start page";
        Boolean newActive = false;


        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .description("google start page")
                .endTime(LocalDateTime.now().plusDays(2))
                .build();

        LinkInfoResponse linkInfoResponseCreate = linkInfoService.createLinkInfo(request);

        UpdateLinkInfoRequest updateRequest = UpdateLinkInfoRequest.builder()
                .id(linkInfoResponseCreate.getId())
                .link(newLink)
                .endTime(newEndTime)
                .description(newDescription)
                .active(newActive)
                .build();
        LinkInfoResponse linkInfoResponseUpdate = linkInfoService.updateLinkInfo(updateRequest);

        assertEquals(linkInfoResponseCreate.getId(), linkInfoResponseUpdate.getId());
        assertEquals(newLink, linkInfoResponseUpdate.getLink());
        assertEquals(newEndTime, linkInfoResponseUpdate.getEndTime());
        assertEquals(newActive, linkInfoResponseUpdate.getActive());
        assertEquals(newDescription, linkInfoResponseUpdate.getDescription());

    }

    @Test
    void whenUpdateLinkInfoThenFailed() {

        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link("http://google.com")
                .description("google start page")
                .endTime(LocalDateTime.now().plusDays(2))
                .build();
        UpdateLinkInfoRequest updateRequest = UpdateLinkInfoRequest.builder()
                .id(UUID.randomUUID())
                .link("http://vk.com")
                .description("vk start page")
                .endTime(LocalDateTime.now().plusDays(1))
                .build();

        linkInfoService.createLinkInfo(request);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> linkInfoService.updateLinkInfo(updateRequest));

        assertEquals("Не удалось найти сущность для обновления по id", thrown.getMessage());
    }

    private LinkInfoServiceLoggingProxy initForTests() {
        LinkInfoRepository repository = new LinkInfoRepositoryImpl();
        LinkInfoService service = new LinkInfoServiceImpl(repository, linkShortenerProperty);
        return new LinkInfoServiceLoggingProxy(service);
    }

}
