package ru.providokhin.repository.impl;

import ru.providokhin.model.LinkInfo;
import ru.providokhin.repository.LinkInfoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final Map<String, LinkInfo> storage = new ConcurrentHashMap<>();

    @Override
    public LinkInfo save(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());

        storage.put(linkInfo.getShortLink(), linkInfo);

        return linkInfo;
    }

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(storage.get(shortLink));
    }

    @Override
    public List<LinkInfo> findAll() {
        return storage.values().stream().toList();
    }
}
