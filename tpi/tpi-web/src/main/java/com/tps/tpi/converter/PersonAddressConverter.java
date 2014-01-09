package com.tps.tpi.converter;

import com.tps.tpi.dao.PersonDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Address;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.entity.PersonAddress;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.AddressDto;
import com.tps.tpi.model.objects.dto.PersonAddressDto;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a PersonAddress entity to a PersonAddressDto dto
 */
public class PersonAddressConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(PersonAddressConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof PersonAddress && dto instanceof PersonAddressDto) {
            PersonAddress entity = (PersonAddress) source;

            ((PersonAddressDto) dto).setPerson(entity.getPerson().getId());
            ((PersonAddressDto) dto).setAddress(mapper.map(entity.getAddress(), AddressDto.class));
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof PersonAddressDto && entity instanceof PersonAddress) {
            PersonAddressDto dto = (PersonAddressDto) source;

            try {
                ((PersonAddress) entity).setPerson(personDao.get(Person.class, dto.getPerson()));
                ((PersonAddress) entity).setAddress(mapper.map(dto.getAddress(), Address.class));
            } catch (PersistenceException e) {
                log.error("Could not retrieve Person entity from db: " + e.getMessage(), e);
                throw new MappingException(e.getMessage(), e);
            }

        }

        return entity;
    }

    @Autowired
    private PersonDao personDao;
}