package com.tps.tpi.converter;

import com.tps.tpi.dao.EducationDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Education;
import com.tps.tpi.model.objects.entity.EducationCertification;
import com.tps.tpi.model.objects.entity.Certification;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.EducationCertificationDto;
import com.tps.tpi.model.objects.dto.CertificationDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a EducationCertification entity to a EducationCertificationDto dto.
 */
public class EducationCertificationConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(EducationCertificationConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof EducationCertification && dto instanceof EducationCertificationDto) {
            EducationCertification entity = (EducationCertification) source;

            ((EducationCertificationDto) dto).setIssueDate(entity.getIssueDate());
            ((EducationCertificationDto) dto).setIssuedBy(entity.getIssuedBy());
            ((EducationCertificationDto) dto).setTitle(entity.getTitle());

            if (entity.getCertification() != null) {
                ((EducationCertificationDto) dto).setCertification(mapper.map(entity.getCertification(), CertificationDto.class));
            }

            if (entity.getEducation() != null) {
                ((EducationCertificationDto) dto).setEducation(entity.getEducation().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof EducationCertificationDto && entity instanceof EducationCertification) {
                EducationCertificationDto dto = (EducationCertificationDto) source;

                ((EducationCertification) entity).setIssueDate(dto.getIssueDate());
                ((EducationCertification) entity).setIssuedBy(dto.getIssuedBy());
                ((EducationCertification) entity).setTitle(dto.getTitle());

                if (dto.getCertification() != null) {
                    ((EducationCertification) entity).setCertification(mapper.map(dto.getCertification(), Certification.class));
                }

                if (StringUtils.isNotBlank(dto.getEducation())) {
                    ((EducationCertification) entity).setEducation(educationDao.get(Education.class, dto.getEducation()));
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