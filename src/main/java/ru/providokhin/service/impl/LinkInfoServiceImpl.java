package ru.providokhin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.providokhin.annotation.LogExecutionTime;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;
import ru.providokhin.exception.NotFoundException;
import ru.providokhin.mapper.LinkInfoMapper;
import ru.providokhin.model.LinkInfo;
import ru.providokhin.property.LinkShortenerProperty;
import ru.providokhin.repository.LinkInfoRepository;
import ru.providokhin.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoMapper linkInfoMapper;
    private final LinkInfoRepository linkInfoRepository;
    private final LinkShortenerProperty linkShortenerProperty;

    @Override
    @LogExecutionTime
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest request) {
        String shortLink = RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength());

        LinkInfo linkInfo = linkInfoMapper.fromCreateRequest(request, shortLink);

        LinkInfo savedLinkInfo = linkInfoRepository.save(linkInfo);

        return linkInfoMapper.toResponse(savedLinkInfo);

    }

    @Override
    @LogExecutionTime
    public LinkInfoResponse getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLinkAndCheckTimeAndActive(shortLink, LocalDateTime.now())
                .map(linkInfoMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Не удалось найти по короткой ссылке: " + shortLink));
    }

    @Override
    @LogExecutionTime
    public List<LinkInfoResponse> findByFilter() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    @Override
    @LogExecutionTime
    public void deleteLinkByID(UUID id) {
        linkInfoRepository.deleteLinkById(id);
    }

    @Override
    @LogExecutionTime
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        LinkInfo linkUpdateInfo = linkInfoRepository.findById(UUID.fromString(request.getId()))
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

        LinkInfo saveLinkInfo = linkInfoRepository.save(linkUpdateInfo);

        return linkInfoMapper.toResponse(linkUpdateInfo);
    }

}
