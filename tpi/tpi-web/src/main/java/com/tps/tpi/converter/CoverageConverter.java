package com.tps.tpi.converter;

import com.tps.tpi.dao.HistoryDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Coverage entity to a CoverageDto dto.
 */
public class CoverageConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(CoverageConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Coverage && dto instanceof CoverageDto) {
            Coverage entity = (Coverage) source;

            if (entity.getCompany() != null) {
                ((CoverageDto) dto).setCompany(mapper.map(entity.getCompany(), CompanyDto.class));
            }

            if (entity.getSkilledRole() != null) {
                ((CoverageDto) dto).setSkilledRole(mapper.map(entity.getSkilledRole(), SkilledRoleDto.class));
            }

            if (entity.getProduct() != null) {
                ((CoverageDto) dto).setProduct(mapper.map(entity.getProduct(), ProductDto.class));
            }

            if (entity.getRegion() != null) {
                ((CoverageDto) dto).setRegion(mapper.map(entity.getRegion(), RegionDto.class));
            }

            if (entity.getCoverageEndorsements() != null) {
                for (CoverageEndorsement endorsement : entity.getCoverageEndorsements()) {
                    ((CoverageDto) dto).addCoverageEndorsement(mapper.map(endorsement, CoverageEndorsementDto.class));
                }

            }

            if (entity.getHistory() != null) {
                ((CoverageDto) dto).setHistory(entity.getHistory().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof CoverageDto && entity instanceof Coverage) {
                CoverageDto dto = (CoverageDto) source;

                if (dto.getCompany() != null) {
                    ((Coverage) entity).setCompany(mapper.map(dto.getCompany(), Company.class));
                }

                if (dto.getSkilledRole() != null) {
                    ((Coverage) entity).setSkilledRole(mapper.map(dto.getSkilledRole(), SkilledRole.class));
                }

                if (dto.getProduct() != null) {
                    ((Coverage) entity).setProduct(mapper.map(dto.getProduct(), Product.class));
                }

                if (dto.getRegion() != null) {
                    ((Coverage) entity).setRegion(mapper.map(dto.getRegion(), Region.class));
                }

                if (StringUtils.isNotBlank(dto.getHistory())) {
                    ((Coverage) entity).setHistory(historyDao.get(History.class, dto.getHistory()));
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

}