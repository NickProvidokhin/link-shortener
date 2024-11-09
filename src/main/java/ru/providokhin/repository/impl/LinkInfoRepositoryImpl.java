package ru.providokhin.repository.impl;

import org.springframework.stereotype.Repository;
import ru.providokhin.model.LinkInfo;
import ru.providokhin.repository.LinkInfoRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
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

    @Override
    public Optional<LinkInfo> findById(UUID id) {
        return storage.values().stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteLinkById(UUID id) {
        Optional<LinkInfo> findById = findById(id);
        findById.ifPresent(it -> storage.remove(it.getShortLink()));
    }
}
