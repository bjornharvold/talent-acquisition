package com.tps.tpi.service;

import com.tps.tpi.AbstractTest;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.enums.*;
import org.dozer.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:11:01 PM
 * Responsibility:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class ReferenceDataServiceIntegrationTest extends AbstractTest {
    private final static Logger log = LoggerFactory.getLogger(ReferenceDataServiceIntegrationTest.class);

    @Test
    public void testGetRegionsWithChildren() {
        assertNotNull("remotingProfileService is null", service);

        try {
            log.info("Grabbing all regions with children...");
            List<RegionDto> list = service.getRegionsWithChildren(null, 0, 100);
            assertNotNull("Result list is null", list);
            log.info("Test regions retrieved successfully");

            log.info("===========================================================================");

            log.info("Now we retrieve the regions again. It should be a lot faster as it is coming from our cache");
            list = service.getRegionsWithChildren(null, 0, 100);
            assertNotNull("Result list is null", list);
            log.info("Test regions retrieved successfully");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Autowired
    private RemotingReferenceDataService service;
}