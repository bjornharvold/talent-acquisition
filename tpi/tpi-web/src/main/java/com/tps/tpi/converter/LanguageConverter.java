package com.tps.tpi.converter;

import com.tps.tpi.dao.EducationDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Education;
import com.tps.tpi.model.objects.entity.Language;
import com.tps.tpi.model.objects.entity.LanguageEndorsement;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.LanguageDto;
import com.tps.tpi.model.objects.dto.LanguageEndorsementDto;
import com.tps.tpi.model.objects.enums.LanguageCd;
import com.tps.tpi.model.objects.enums.LanguageLevelCd;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Language entity to a LanguageDto dto.
 */
public class LanguageConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(LanguageConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Language && dto instanceof LanguageDto) {
            Language entity = (Language) source;

            ((LanguageDto) dto).setReadwrite(entity.getReadwrite().name());
            ((LanguageDto) dto).setVerbal(entity.getVerbal().name());
            ((LanguageDto) dto).setType(entity.getType().name());

            if (entity.getLanguageEndorsements() != null) {
                for (LanguageEndorsement endorsement : entity.getLanguageEndorsements()) {
                    ((LanguageDto) dto).addLanguageEndorsement(mapper.map(endorsement, LanguageEndorsementDto.class));
                }
            }

            if (entity.getEducation() != null) {
                ((LanguageDto) dto).setEducation(entity.getEducation().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof LanguageDto && entity instanceof Language) {
                LanguageDto dto = (LanguageDto) source;

                ((Language) entity).setReadwrite(LanguageLevelCd.valueOf(dto.getReadwrite()));
                ((Language) entity).setVerbal(LanguageLevelCd.valueOf(dto.getVerbal()));
                ((Language) entity).setType(LanguageCd.valueOf(dto.getType()));

                if (StringUtils.isNotBlank(dto.getEducation())) {
                    ((Language) entity).setEducation(educationDao.get(Education.class, dto.getEducation()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private EducationDao educationDao;

}