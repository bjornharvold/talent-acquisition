package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.BiographySkillDto;
import com.tps.tpi.model.objects.dto.BiographySkillEndorsementDto;
import com.tps.tpi.model.objects.dto.SkillDto;
import com.tps.tpi.model.objects.enums.ProficiencyCd;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a BiographySkill entity to a BiographySkillDto dto.
 */
public class BiographySkillConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(BiographySkillConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof BiographySkill && dto instanceof BiographySkillDto) {
            BiographySkill entity = (BiographySkill) source;

            ((BiographySkillDto) dto).setProficiency(entity.getProficiency().name());
            ((BiographySkillDto) dto).setYears(entity.getYears());

            if (entity.getSkill() != null) {
                ((BiographySkillDto) dto).setSkill(mapper.map(entity.getSkill(), SkillDto.class));
            }

            if (entity.getBiographySkillEndorsements() != null) {
                for (BiographySkillEndorsement skillEndorsement : entity.getBiographySkillEndorsements()) {
                    ((BiographySkillDto) dto).addBiographySkillEndorsement(mapper.map(skillEndorsement, BiographySkillEndorsementDto.class));
                }
            }

            if (entity.getBiography() != null) {
                ((BiographySkillDto) dto).setBiography(entity.getBiography().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof BiographySkillDto && entity instanceof BiographySkill) {
                BiographySkillDto dto = (BiographySkillDto) source;

                ((BiographySkill) entity).setProficiency(ProficiencyCd.valueOf(dto.getProficiency()));
                ((BiographySkill) entity).setYears(dto.getYears());

                if (dto.getSkill() != null) {
                    ((BiographySkill) entity).setSkill(mapper.map(dto.getSkill(), Skill.class));
                }

                if (StringUtils.isNotBlank(dto.getBiography())) {
                    ((BiographySkill) entity).setBiography(biographyDao.get(Biography.class, dto.getBiography()));
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