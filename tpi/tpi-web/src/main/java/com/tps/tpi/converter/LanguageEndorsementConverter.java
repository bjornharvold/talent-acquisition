package com.tps.tpi.converter;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Endorsement;
import com.tps.tpi.model.objects.entity.Language;
import com.tps.tpi.model.objects.entity.LanguageEndorsement;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.EndorsementDto;
import com.tps.tpi.model.objects.dto.LanguageEndorsementDto;
import com.tps.tpi.dao.LanguageDao;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a LanguageEndorsement entity to a LanguageEndorsementDto dto.
 */
public class LanguageEndorsementConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(LanguageEndorsementConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof LanguageEndorsement && dto instanceof LanguageEndorsementDto) {
            LanguageEndorsement entity = (LanguageEndorsement) source;

            ((LanguageEndorsementDto) dto).setEndorsement(mapper.map(entity.getEndorsement(), EndorsementDto.class));

            if (entity.getLanguage() != null) {
                ((LanguageEndorsementDto) dto).setLanguage(entity.getLanguage().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof LanguageEndorsementDto && entity instanceof LanguageEndorsement) {
                LanguageEndorsementDto dto = (LanguageEndorsementDto) source;

                ((LanguageEndorsement) entity).setEndorsement(mapper.map(dto.getEndorsement(), Endorsement.class));

                if (StringUtils.isNotBlank(dto.getLanguage())) {
                    ((LanguageEndorsement) entity).setLanguage(languageDao.get(Language.class, dto.getLanguage()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private LanguageDao languageDao;

}