package ru.providokhin.repository;

import ru.providokhin.model.LinkInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository {

    LinkInfo save(LinkInfo linkInfo);

    Optional<LinkInfo> findByShortLink(String shortLink);

    List<LinkInfo> findAll();

    Optional<LinkInfo> findById(UUID id);

    void deleteLinkById(UUID id);

    Optional<LinkInfo> findByShortLinkAndCheckTimeAndActive(String shortLink, LocalDateTime time);

}
