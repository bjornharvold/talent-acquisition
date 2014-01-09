package com.tps.tpi.converter;

import com.tps.tpi.dao.HistoryDao;
import com.tps.tpi.dao.CityDao;
import com.tps.tpi.dao.DepartmentDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.enums.EmploymentTypeCd;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Position entity to a PositionDto dto.
 */
public class PositionConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(PositionConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Position && dto instanceof PositionDto) {
            Position entity = (Position) source;

            ((PositionDto) dto).setFrom(entity.getFrom());
            ((PositionDto) dto).setTo(entity.getTo());
            ((PositionDto) dto).setType(entity.getType().name());

            if (entity.getCity() != null) {
                ((PositionDto) dto).setCity(entity.getCity().getId());
                ((PositionDto) dto).setCityName(entity.getCity().getShortName());
            }

            if (entity.getSkilledRole() != null) {
                ((PositionDto) dto).setSkilledRole(mapper.map(entity.getSkilledRole(), SkilledRoleDto.class));
            }

            if (entity.getDepartment() != null) {
                ((PositionDto) dto).setDepartment(entity.getDepartment().getId());
                ((PositionDto) dto).setDepartmentName(entity.getDepartment().getShortName());

                if (entity.getDepartment().getDivision() != null) {
                    ((PositionDto) dto).setCompanyName(entity.getDepartment().getDivision().getCompany().getShortName());
                } else {
                    log.error("Division is null for Department: " + entity.getDepartment().getShortName());
                }
            }

            if (entity.getPositionEndorsements() != null) {
                for (PositionEndorsement endorsement : entity.getPositionEndorsements()) {
                    ((PositionDto) dto).addPositionEndorsement(mapper.map(endorsement, PositionEndorsementDto.class));
                }
            }

            if (entity.getHistory() != null) {
                ((PositionDto) dto).setHistory(entity.getHistory().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof PositionDto && entity instanceof Position) {
                PositionDto dto = (PositionDto) source;

                if (dto.getCity() != null) {
                    ((Position) entity).setCity(cityDao.get(City.class, dto.getCity()));
                }

                if (dto.getSkilledRole() != null) {
                    ((Position) entity).setSkilledRole(mapper.map(dto.getSkilledRole(), SkilledRole.class));
                }

                if (dto.getDepartment() != null) {
                    ((Position) entity).setDepartment(departmentDao.get(Department.class, dto.getDepartment()));
                }

                ((Position) entity).setFrom(dto.getFrom());
                ((Position) entity).setTo(dto.getTo());
                ((Position) entity).setType(EmploymentTypeCd.valueOf(dto.getType()));

                if (StringUtils.isNotBlank(dto.getHistory())) {
                    ((Position) entity).setHistory(historyDao.get(History.class, dto.getHistory()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DepartmentDao departmentDao;

}