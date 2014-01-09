package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Address;
import com.tps.tpi.model.objects.entity.City;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.AddressDto;
import com.tps.tpi.model.objects.enums.AddressTypeCd;
import com.tps.tpi.dao.CityDao;
import com.tps.tpi.exception.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.dozer.MappingException;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 12:20:46 PM
 * Responsibility:
 */
public class AddressConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(AddressConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Address && dto instanceof AddressDto) {
            Address entity = (Address) source;

            ((AddressDto) dto).setAddress1(entity.getAddress1());
            ((AddressDto) dto).setAddress2(entity.getAddress2());
            ((AddressDto) dto).setState(entity.getState());
            ((AddressDto) dto).setZip(entity.getZip());
            ((AddressDto) dto).setCity(entity.getCity().getId());
            ((AddressDto) dto).setCityName(entity.getCity().getShortName());
            ((AddressDto) dto).setType(entity.getType().name());

        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {
        if (source instanceof AddressDto && entity instanceof Address) {
            AddressDto dto = (AddressDto) source;

            try {
                ((Address) entity).setAddress1(dto.getAddress1());
                ((Address) entity).setAddress2(dto.getAddress2());
                ((Address) entity).setState(dto.getState());
                ((Address) entity).setZip(dto.getZip());
                ((Address) entity).setCity(cityDao.get(City.class, dto.getCity()));
                ((Address) entity).setType(AddressTypeCd.valueOf(dto.getType()));
            } catch (PersistenceException e) {
                log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
                throw new MappingException(e.getMessage(), e);
            }
        }

        return entity;
    }

    @Autowired
    private CityDao cityDao;
}
