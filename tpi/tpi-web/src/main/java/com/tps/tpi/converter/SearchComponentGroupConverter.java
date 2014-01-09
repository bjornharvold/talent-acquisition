package com.tps.tpi.converter;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.exception.CacheException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.SearchComponentDto;
import com.tps.tpi.model.objects.dto.SearchComponentGroupDto;
import com.tps.tpi.model.objects.dto.SearchDto;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.enums.SearchComponentGroupTypeCd;
import com.tps.tpi.model.objects.lite.PersonLite;
import com.tps.tpi.service.ProfileService;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a SearchComponentGroup entity to a SearchComponentGroupDto dto. We are assuming the SearchComponentGroup entity will ALWAYS
 * be created before the dto so when we convert back to the entity, we don't have to update every field because they will
 * always be there.
 */
public class SearchComponentGroupConverter extends AbstractConverter {
    private static final Logger log = LoggerFactory.getLogger(SearchComponentGroupConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof SearchComponentGroup && dto instanceof SearchComponentGroupDto) {
            SearchComponentGroup entity = (SearchComponentGroup) source;

            ((SearchComponentGroupDto) dto).setType(entity.getType().name());

            if (entity.getComponents() != null) {
                for (SearchComponent component : entity.getComponents()) {
                    ((SearchComponentGroupDto) dto).addComponent(mapper.map(component, SearchComponentDto.class));
                }
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof SearchComponentGroupDto && entity instanceof SearchComponentGroup) {
            SearchComponentGroupDto dto = (SearchComponentGroupDto) source;

            ((SearchComponentGroup) entity).setType(SearchComponentGroupTypeCd.valueOf(dto.getType()));

            if (dto.getComponents() != null) {
                for (SearchComponentDto component : dto.getComponents()) {
                    ((SearchComponentGroup) entity).addComponent(mapper.map(component, SearchComponent.class));
                }
            }
        }

        return entity;
    }
}