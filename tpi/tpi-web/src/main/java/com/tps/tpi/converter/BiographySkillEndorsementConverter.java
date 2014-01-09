package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographySkillDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.BiographySkill;
import com.tps.tpi.model.objects.entity.BiographySkillEndorsement;
import com.tps.tpi.model.objects.entity.Endorsement;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.BiographySkillEndorsementDto;
import com.tps.tpi.model.objects.dto.EndorsementDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a BiographySkillEndorsement entity to a BiographySkillEndorsementDto dto.
 */
public class BiographySkillEndorsementConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(BiographySkillEndorsementConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof BiographySkillEndorsement && dto instanceof BiographySkillEndorsementDto) {
            BiographySkillEndorsement entity = (BiographySkillEndorsement) source;

            ((BiographySkillEndorsementDto) dto).setEndorsement(mapper.map(entity.getEndorsement(), EndorsementDto.class));

            if (entity.getBiographySkill() != null) {
                ((BiographySkillEndorsementDto) dto).setBiographySkill(entity.getBiographySkill().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof BiographySkillEndorsementDto && entity instanceof BiographySkillEndorsement) {
                BiographySkillEndorsementDto dto = (BiographySkillEndorsementDto) source;

                ((BiographySkillEndorsement) entity).setEndorsement(mapper.map(dto.getEndorsement(), Endorsement.class));

                if (StringUtils.isNotBlank(dto.getBiographySkill())) {
                    ((BiographySkillEndorsement) entity).setBiographySkill(biographySkillDao.get(BiographySkill.class, dto.getBiographySkill()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private BiographySkillDao biographySkillDao;

}