package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Endorsement;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.EndorsementDto;
import com.tps.tpi.model.objects.lite.PersonLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Endorsement entity to a EndorsementDto dto.
 */
public class EndorsementConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(EndorsementConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Endorsement && dto instanceof EndorsementDto) {
            Endorsement entity = (Endorsement) source;

            ((EndorsementDto) dto).setEndorsedOn(entity.getEndorsedOn());
            ((EndorsementDto) dto).setStars(entity.getStars());

            if (entity.getEndorser() != null) {
                ((EndorsementDto) dto).setEndorser(mapper.map(entity.getEndorser(), PersonLite.class));
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof EndorsementDto && entity instanceof Endorsement) {
            EndorsementDto dto = (EndorsementDto) source;

            ((Endorsement) entity).setEndorsedOn(dto.getEndorsedOn());
            ((Endorsement) entity).setStars(dto.getStars());

            if (dto.getEndorser() != null) {
                ((Endorsement) entity).setEndorser(mapper.map(dto.getEndorser(), Person.class));
            }

        }

        return entity;
    }

}