package ru.providokhin.repository;

import ru.providokhin.model.LinkInfo;

import java.util.List;
import java.util.Optional;

public interface LinkInfoRepository {

    public LinkInfo save(LinkInfo linkInfo);

    public Optional<LinkInfo> findByShortLink(String shortLink);

    public List<LinkInfo> findAll();
}
