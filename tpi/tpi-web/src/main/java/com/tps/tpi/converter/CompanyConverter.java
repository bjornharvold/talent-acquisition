package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.Company;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.CompanyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Company entity to a CompanyDto dto
 */
public class CompanyConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(CompanyConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Company && dto instanceof CompanyDto) {
            Company entity = (Company) source;

            ((CompanyDto) dto).setContactName(entity.getContactName());
            ((CompanyDto) dto).setContactPhone(entity.getContactPhone());

            if (entity.getParent() != null) {
                ((CompanyDto) dto).setParent(entity.getParent().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof CompanyDto && entity instanceof Company) {
                CompanyDto dto = (CompanyDto) source;

                // we're not going to be creating companies with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}