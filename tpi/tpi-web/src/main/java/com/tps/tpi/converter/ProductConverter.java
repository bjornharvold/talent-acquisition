package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.Product;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Product entity to a ProductDto dto
 */
public class ProductConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(ProductConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Product && dto instanceof ProductDto) {
            Product entity = (Product) source;

            if (entity.getCompany() != null) {
                ((ProductDto) dto).setCompany(entity.getCompany().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof ProductDto && entity instanceof Product) {
                ProductDto dto = (ProductDto) source;

                // we're not going to be creating companies with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}