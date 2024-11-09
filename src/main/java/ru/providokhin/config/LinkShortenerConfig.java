package ru.providokhin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.providokhin.property.LinkShortenerProperty;
import ru.providokhin.repository.LinkInfoRepository;
import ru.providokhin.service.LinkInfoService;
import ru.providokhin.service.impl.LinkInfoServiceImpl;
import ru.providokhin.service.impl.LinkInfoServiceLoggingProxy;

@Configuration
public class LinkShortenerConfig {

    @Bean
    public LinkInfoService linkInfoService(LinkInfoRepository linkInfoRepository, LinkShortenerProperty linkShortenerProperty) {

        LinkInfoService linkInfoService = new LinkInfoServiceImpl(linkInfoRepository, linkShortenerProperty);
        LinkInfoService linkInfoServiceLoggingProxy = new LinkInfoServiceLoggingProxy(linkInfoService);

        return linkInfoServiceLoggingProxy;
    }
}
