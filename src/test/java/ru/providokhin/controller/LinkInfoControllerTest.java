package ru.providokhin.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;
import ru.providokhin.dto.common.CommonRequest;
import ru.providokhin.dto.common.CommonResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LinkInfoControllerTest {

    @Autowired
    LinkInfoController linkInfoController;

    CreateLinkInfoRequest body = CreateLinkInfoRequest.builder()
            .link("https://habr.com")
            .endTime(LocalDateTime.now().plusHours(12))
            .description("habr's description")
            .active(true)
            .build();

    @Test
    public void whenPostCreateLinkInfoThenSuccess() {
        CommonRequest<CreateLinkInfoRequest> request = new CommonRequest<>(body);

        CommonResponse<LinkInfoResponse> response = linkInfoController.postCreateLinkInfo(request);

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void whenUpdateLinkInfoThenSuccess() {
        CommonRequest<CreateLinkInfoRequest> createRequest = new CommonRequest<>(body);

        CommonResponse<LinkInfoResponse> createResponse = linkInfoController.postCreateLinkInfo(createRequest);

        UpdateLinkInfoRequest updateBody = UpdateLinkInfoRequest.builder()
                .id(createResponse.getBody().getId())
                .link("https://google.com")
                .active(false)
                .build();
        CommonRequest<UpdateLinkInfoRequest> updateRequest = new CommonRequest<>(updateBody);

        CommonResponse<LinkInfoResponse> updateResponse = linkInfoController.patchUpdateLinkInfo(updateRequest);

        assertNotNull(updateResponse.getBody());
    }

    @Test
    public void whenDeleteLinkInfoThenSuccess() {
        CommonRequest<CreateLinkInfoRequest> createRequest = new CommonRequest<>(body);

        CommonResponse<LinkInfoResponse> createResponse = linkInfoController.postCreateLinkInfo(createRequest);

        CommonResponse<LinkInfoResponse> deleteResponse = linkInfoController.deleteLinkInfo(createResponse.getBody().getId());

        assertNotNull(deleteResponse.getId());
    }

    @Test
    public void whenGetLinkInfoThenSuccess() {
        CommonRequest<CreateLinkInfoRequest> createRequest = new CommonRequest<>(body);

        CommonResponse<LinkInfoResponse> createResponse = linkInfoController.postCreateLinkInfo(createRequest);

        CommonResponse<List<LinkInfoResponse>> getResponse = linkInfoController.getLinkInfoByFilter();

        assertNotNull(getResponse.getBody());
    }

}