package ru.providokhin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.dto.UpdateLinkInfoRequest;
import ru.providokhin.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

@Deprecated
@Slf4j
public class LinkInfoServiceLoggingProxy implements LinkInfoService {

    private final LinkInfoService linkInfoService;

    public LinkInfoServiceLoggingProxy(LinkInfoService linkInfoService) {
        this.linkInfoService = linkInfoService;
    }

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.createLinkInfo(request);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода createLinkInfo: " + stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse getByShortLink(String shortLink) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.getByShortLink(shortLink);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода getByShortLink: " + stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public List<LinkInfoResponse> findByFilter() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.findByFilter();
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода findByFilter: " + stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public void deleteLinkByID(UUID id) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            linkInfoService.deleteLinkByID(id);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода deleteLinkByID: " + stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest updateLinkInfoRequest) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.updateLinkInfo(updateLinkInfoRequest);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода updateLinkInfo: " + stopWatch.getTotalTimeMillis());
        }
    }
}
