package ru.providokhin.service;

import ru.providokhin.dto.CreateLinkInfoRequest;

public interface LinkInfoService {
    String createShortLink(CreateLinkInfoRequest createLinkInfoRequest);
}
