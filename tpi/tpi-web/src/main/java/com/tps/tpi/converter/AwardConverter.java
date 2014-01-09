package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Award;
import com.tps.tpi.model.objects.entity.Biography;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.AwardDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts an Award entity to an AwardDto dto.
 */
public class AwardConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(AwardConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Award && dto instanceof AwardDto) {
            Award entity = (Award) source;

            ((AwardDto) dto).setAwardName(entity.getAwardName());
            ((AwardDto) dto).setIssuedDate(entity.getIssuedDate());
            ((AwardDto) dto).setIssuer(entity.getIssuer());

            if (entity.getBiography() != null) {
                ((AwardDto) dto).setBiography(entity.getBiography().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof AwardDto && entity instanceof Award) {
                AwardDto dto = (AwardDto) source;

                ((Award) entity).setAwardName(dto.getAwardName());
                ((Award) entity).setIssuer(dto.getIssuer());
                ((Award) entity).setIssuedDate(dto.getIssuedDate());

                if (StringUtils.isNotBlank(dto.getBiography())) {
                    ((Award) entity).setBiography(biographyDao.get(Biography.class, dto.getBiography()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private BiographyDao biographyDao;
}