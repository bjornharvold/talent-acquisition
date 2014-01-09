package com.tps.tpi.converter;

import com.tps.tpi.dao.EducationDao;
import com.tps.tpi.dao.SchoolDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Degree;
import com.tps.tpi.model.objects.entity.Education;
import com.tps.tpi.model.objects.entity.School;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.DegreeDto;
import com.tps.tpi.model.objects.enums.DegreeTypeCd;
import com.tps.tpi.model.objects.enums.MajorTypeCd;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Degree entity to a DegreeDto dto.
 */
public class DegreeConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(DegreeConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Degree && dto instanceof DegreeDto) {
            Degree entity = (Degree) source;

            ((DegreeDto) dto).setCompletedDate(entity.getCompletedDate());
            ((DegreeDto) dto).setMajor(entity.getMajor().name());
            ((DegreeDto) dto).setType(entity.getType().name());

            if (entity.getSchool() != null) {
                ((DegreeDto) dto).setSchool(entity.getSchool().getId());
                ((DegreeDto) dto).setSchoolName(entity.getSchool().getLongName());
            }

            if (entity.getEducation() != null) {
                ((DegreeDto) dto).setEducation(entity.getEducation().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof DegreeDto && entity instanceof Degree) {
                DegreeDto dto = (DegreeDto) source;

                ((Degree) entity).setCompletedDate(dto.getCompletedDate());
                ((Degree) entity).setMajor(MajorTypeCd.valueOf(dto.getMajor()));
                ((Degree) entity).setType(DegreeTypeCd.valueOf(dto.getType()));

                if (dto.getSchool() != null) {
                    ((Degree) entity).setSchool(schoolDao.get(School.class, dto.getSchool()));
                }

                if (StringUtils.isNotBlank(dto.getEducation())) {
                    ((Degree) entity).setEducation(educationDao.get(Education.class, dto.getEducation()));
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

    @Autowired
    private SchoolDao schoolDao;
}