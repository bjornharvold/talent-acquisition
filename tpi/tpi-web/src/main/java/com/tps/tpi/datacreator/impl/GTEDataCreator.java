/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.Company;
import com.tps.tpi.model.objects.entity.Source;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import com.tps.tpi.service.CorporateService;
import com.tps.tpi.service.SourcingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 */
@Component("gteDataCreator")
public class GTEDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(GTEDataCreator.class);
    private final CorporateService service;
    private final SourcingService sourcingService;
    private final static String GTE = "GTE";

    @Autowired
    public GTEDataCreator(CorporateService service, SourcingService sourcingService) {
        this.service = service;
        this.sourcingService = sourcingService;
    }

    public void create() throws DataCreatorException {

        try {
            Company company = service.getCompanyByCode(GTE);

            if (company == null) {
                company = new Company();
                company.setCode(GTE);
                company.setContactName("Robert Davidson");
                company.setContactPhone("2125551212");
                company.setDescription("GTE is the holding company of all unemployed people");
                company.setLongName(GTE);
                company.setShortName(GTE);
                company.setRef("http://www.talentportfoliosolutions.com");

                company = service.saveCompany(company);

                // source GTE
                Source source = new Source(company.getId(), SourceTypeCd.COMPANY, company.getShortName(), null, null, null, null, new Date());
                source = sourcingService.saveSourceFromGTE(source);
                log.debug("Persisted GTE company: " + company);
            } else {
                log.debug("GTE company already exists");
            }
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            throw new DataCreatorException(e.getMessage(), e);
        }
    }
}