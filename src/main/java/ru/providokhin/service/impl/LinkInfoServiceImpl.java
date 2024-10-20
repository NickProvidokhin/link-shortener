package ru.providokhin.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.exception.NotFoundException;
import ru.providokhin.model.LinkInfo;
import ru.providokhin.repository.LinkInfoRepository;
import ru.providokhin.service.LinkInfoService;

import java.util.List;

import static ru.providokhin.util.Constants.LINK_LENGTH;

public class LinkInfoServiceImpl implements LinkInfoService {

    LinkInfoRepository linkInfoRepository;

    public LinkInfoServiceImpl(LinkInfoRepository linkInfoRepository) {
        this.linkInfoRepository = linkInfoRepository;
    }

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest request) {
        String shortLink = RandomStringUtils.randomAlphanumeric(LINK_LENGTH);
        LinkInfo linkInfo = LinkInfo.builder()
                .shortLink(shortLink)
                .link(request.getLink())
                .description(request.getDescription())
                .endTime(request.getEndTime())
                .activate(request.getActivate())
                .openingCount(0L)
                .build();
        LinkInfo saveLinkInfo = linkInfoRepository.save(linkInfo);
        return toResponse(saveLinkInfo);

    }

    @Override
    public LinkInfoResponse getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .map(it -> toResponse(it))
                .orElseThrow(() -> new NotFoundException("Не удалось найти по короткой ссылке: " + shortLink));
    }

    @Override
    public List<LinkInfoResponse> findByFilter() {
        return linkInfoRepository.findAll().stream()
                .map(it -> toResponse(it))
                .toList();
    }

    private LinkInfoResponse toResponse(LinkInfo linkInfo) {
        return LinkInfoResponse.builder()
                .id(linkInfo.getId())
                .link(linkInfo.getLink())
                .openingCount(linkInfo.getOpeningCount())
                .shortLink(linkInfo.getShortLink())
                .endTime(linkInfo.getEndTime())
                .description(linkInfo.getDescription())
                .activate(linkInfo.getActivate())
                .build();
    }
}
