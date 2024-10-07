package ru.providokhin.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.service.LinkInfoService;

import java.util.HashMap;

public class LinkInfoServiceImpl implements LinkInfoService {
    HashMap<String, CreateLinkInfoRequest> linkInfoRequestHashMap = new HashMap<>();
    final int LINK_LENGTH = 10;

    @Override
    public String createShortLink(CreateLinkInfoRequest createLinkInfoRequest) {
        String shortLink = RandomStringUtils.randomAlphanumeric(LINK_LENGTH);
        linkInfoRequestHashMap.put(shortLink, createLinkInfoRequest);
        return shortLink;
    }
}
