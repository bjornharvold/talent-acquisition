package com.tps.tpi.service.impl;

import com.tps.tpi.dao.SourceDao;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Company;
import com.tps.tpi.model.objects.entity.Source;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import com.tps.tpi.service.CorporateService;
import com.tps.tpi.service.SourcingService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Dec 12, 2009
 * Time: 12:37:48 AM
 * Responsibility:
 */
@Service("sourcingService")
public class SourcingServiceImpl implements SourcingService {
    private static final Logger log = LoggerFactory.getLogger(SourcingServiceImpl.class);
    private final SourceDao sourceDao;
    private final CorporateService corporateService;

    @Autowired
    public SourcingServiceImpl(SourceDao sourceDao, CorporateService corporateService) {
        this.sourceDao = sourceDao;
        this.corporateService = corporateService;
    }

    @Override
    public Source saveSource(Source source) throws DomainException {
        if (source == null) {
            throw new DomainException("error.missing.data", "source");
        }

        try {
            source = sourceDao.save(source);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return source;
    }

    @Override
    public Source getSource(String id) throws DomainException {
        Source result = null;

        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            result = sourceDao.get(Source.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Convenience method when the sourcer is GTE
     * @param source
     * @return
     * @throws DomainException
     */
    @Override
    public Source saveSourceFromGTE(Source source) throws DomainException {
        Company company = corporateService.getCompanyByCode("GTE");

        if (company == null) {
            log.error("GTE company doesn't not exist in the database");
            throw new DomainException("error.missing.data", "Company: GTE");
        }

        source.setSourcerId(company.getId());
        source.setSourcerType(SourceTypeCd.COMPANY);
        source.setSourcerDescription("GTE");
        source.setWorkflowId("GTE");
        
        return saveSource(source);
    }
}
