package ru.providokhin.service;

import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;

import java.util.List;
import java.util.UUID;

public interface LinkInfoService {

    LinkInfoResponse createLinkInfo(CreateLinkInfoRequest request);

    LinkInfoResponse getByShortLink(String shortLink);

    List<LinkInfoResponse> findByFilter();

    void deleteLinkByID(UUID id);

    LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest updateLinkInfoRequest);
}
