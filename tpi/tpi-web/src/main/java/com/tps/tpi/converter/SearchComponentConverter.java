package com.tps.tpi.converter;

import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.SearchComponentDto;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.SearchComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a SearchComponent entity to a SearchComponentDto dto. We are assuming the SearchComponent entity will ALWAYS
 * be created before the dto so when we convert back to the entity, we don't have to update every field because they will
 * always be there.
 */
public class SearchComponentConverter extends AbstractConverter {
    private static final Logger log = LoggerFactory.getLogger(SearchComponentConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof SearchComponent && dto instanceof SearchComponentDto) {
            SearchComponent entity = (SearchComponent) source;

            ((SearchComponentDto) dto).setSearchMap(entity.getSearchMap());

        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof SearchComponentDto && entity instanceof SearchComponent) {
            SearchComponentDto dto = (SearchComponentDto) source;

            ((SearchComponent) entity).setSearchMap(dto.getSearchMap());

        }

        return entity;
    }
}