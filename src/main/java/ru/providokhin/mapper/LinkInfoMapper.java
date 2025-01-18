package ru.providokhin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.providokhin.dto.CreateLinkInfoRequest;
import ru.providokhin.dto.LinkInfoResponse;
import ru.providokhin.model.LinkInfo;

@Mapper(componentModel = "spring")
public interface LinkInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "openingCount", constant = "0L")
    LinkInfo fromCreateRequest(CreateLinkInfoRequest request, String shortLink);

    LinkInfoResponse toResponse(LinkInfo linkInfo);
}
