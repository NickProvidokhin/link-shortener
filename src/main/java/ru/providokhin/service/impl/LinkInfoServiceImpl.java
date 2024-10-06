package ru.providokhin.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.service.LinkInfoService;

import java.util.HashMap;

public class LinkInfoServiceImpl implements LinkInfoService {

    @Override
    public String createShortLink(CreateLinkInfoRequest createLinkInfoRequest) {
        final int linkLength = 10;
        String shortLink = RandomStringUtils.randomAlphanumeric(linkLength);
        HashMap<String, CreateLinkInfoRequest> linkInfoRequestHashMap = new HashMap<>();
        linkInfoRequestHashMap.put(shortLink, createLinkInfoRequest);
        return shortLink;
    }
}
