package ru.providokhin.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;
import ru.providokhin.exception.NotFoundException;
import ru.providokhin.model.LinkInfo;
import ru.providokhin.property.LinkShortenerProperty;
import ru.providokhin.repository.LinkInfoRepository;
import ru.providokhin.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoRepository linkInfoRepository;
    private final LinkShortenerProperty linkShortenerProperty;

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest request) {
        String shortLink = RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength());
        LinkInfo linkInfo = LinkInfo.builder()
                .shortLink(shortLink)
                .link(request.getLink())
                .description(request.getDescription())
                .endTime(request.getEndTime())
                .active(request.getActive())
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

    public void deleteLinkByID(UUID id) {
        linkInfoRepository.deleteLinkById(id);
    }

    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        LinkInfo linkUpdateInfo = linkInfoRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Не удалось найти сущность для обновления по id"));

        if (request.getLink() != null) {
            linkUpdateInfo.setLink(request.getLink());
        }
        if (request.getEndTime() != null) {
            linkUpdateInfo.setEndTime(request.getEndTime());
        }
        if (request.getDescription() != null) {
            linkUpdateInfo.setDescription(request.getDescription());
        }
        if (request.getActive() != null) {
            linkUpdateInfo.setActive(request.getActive());
        }

        return toResponse(linkUpdateInfo);
    }

    private LinkInfoResponse toResponse(LinkInfo linkInfo) {
        return LinkInfoResponse.builder()
                .id(linkInfo.getId())
                .link(linkInfo.getLink())
                .openingCount(linkInfo.getOpeningCount())
                .shortLink(linkInfo.getShortLink())
                .endTime(linkInfo.getEndTime())
                .description(linkInfo.getDescription())
                .active(linkInfo.getActive())
                .build();
    }
}
