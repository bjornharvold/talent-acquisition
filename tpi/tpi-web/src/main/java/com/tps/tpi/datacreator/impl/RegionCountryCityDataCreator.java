/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.City;
import com.tps.tpi.model.objects.entity.Country;
import com.tps.tpi.model.objects.entity.Region;
import com.tps.tpi.model.objects.entity.Source;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import com.tps.tpi.service.ReferenceDataService;
import com.tps.tpi.service.SourcingService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * It expects a regular xml file to parse and create skills and skillgroup objects for.
 * Xml looks like this:
 * <regions>
 * <region code="region.africa" en_US="Africa">
 * <region code="region.africa.east" en_US="Eastern Africa">
 * <country code="country.burundi">Burundi</country>
 * <country code="country.comoros">Comoros</country>
 */
@Component("regionCountryDataCreator")
public class RegionCountryCityDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(RegionCountryCityDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("regions_countries_cities.xml");

    @Autowired
    public RegionCountryCityDataCreator(ReferenceDataService service, SourcingService sourcingService) {
        this.service = service;
        this.sourcingService = sourcingService;
    }

    public void create() throws DataCreatorException {

        try {
            if (file.exists()) {
                processCreation();
            } else {
                throw new DataCreatorException("XML file could not be found");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.debug("Populated " + populated + " regions in db");
        log.debug("Omitted " + omitted + " regions from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processRegions();

            // uncomment if you want the class to create resource bundle string for default language
            // handleResourceBundleEntries();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * Print out all resource bundle entries for region, country and city
     */
    private void handleResourceBundleEntries() {
        try {
            List<Region> regions = service.getRegions(null, null, null);
            List<Country> countries = service.getCountries(null, null, null);
            List<City> cities = service.getCities(null, null, null);

            // print statements for resource bundles
            System.out.println("#==================================== REGIONS ====================================");
            for (Region region : regions) {
                System.out.println(region.getCode() + "=" + region.getShortName());
            }
            System.out.println("#==================================== END REGIONS ====================================");

            // print statements for resource bundles
            System.out.println("#==================================== COUNTRIES ====================================");
            for (Country country : countries) {
                System.out.println(country.getCode() + "=" + country.getShortName());
            }
            System.out.println("#==================================== END COUNTRIES ====================================");

            // print statements for resource bundles
            System.out.println("#==================================== CITIES ====================================");
            for (City city : cities) {
                System.out.println(city.getCode() + "=" + city.getShortName());
            }
            System.out.println("#==================================== END CITIES ====================================");
        } catch (DomainException e) {
            log.error("Could not retrieve lists: " + e.getMessage(), e);
        }

    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws java.io.IOException
     * @throws DataCreatorException
     */
    private void processRegions() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            Element skills = document.getRootElement();

            List<Element> regions = skills.elements();

            if (regions != null) {

                for (Element e : regions) {
                    processRegion(null, e);
                }

            }


        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * Handles parsing out the. This method will go commit and recurse down into the xml tree
     *
     * @param region
     */
    private Region processRegion(Region parent, Element region) throws DataCreatorException {
        // first we commit the region
        Region r = null;
        String code = region.attributeValue("code");
        String defaultLanguage = region.attributeValue("en_US");

        try {
            if (StringUtils.isNotBlank(code)) {
                r = service.getRegionByCode(code);

                if (r == null) {
                    log.debug("Region with code: " + code + " does not exist. Create new one.");
                    r = new Region();
                    r.setCode(code);
                    r.setShortName(defaultLanguage);

                    if (parent != null) {
                        r.setParent(parent);
                    }
                    r = service.saveRegion(r);
                    log.debug("Saved new Region in db: " + r);

                    // now we source it
                    Source source = new Source(r.getId(), SourceTypeCd.REGION, r.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);

                    populated++;
                } else {
                    log.debug("Region already exists: " + r);
                    omitted++;
                }

                // now we create countries or child subRegions for
                List<Element> subRegions = region.elements("region");

                if (subRegions != null) {
                    List<Region> children = new ArrayList<Region>();

                    for (Element child : subRegions) {
                        children.add(processRegion(r, child));
                    }

                    r.setChildren(children);
                }

                List<Element> countriesE = region.elements("country");

                if (countriesE != null) {
                    List<Country> countries = new ArrayList<Country>();

                    for (Element country : countriesE) {
                        countries.add(processCountry(r, country));
                    }

                    r.setCountries(countries);
                }

                service.updateRegion(r);
            } else {
                log.error("Region element is missing an attribute named code");
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return r;
    }

    /**
     * Saves a skill and associates it to a skillgroup
     *
     * @param region
     * @param country
     */
    private Country processCountry(Region region, Element country) throws DataCreatorException {
        Country c = null;
        String code = country.attributeValue("code");
        String defaultLanguage = country.attributeValue("en_US");

        try {
            c = service.getCountryByCode(code);

            if (c == null) {
                c = new Country();
                c.setCode(code);
                c.setShortName(defaultLanguage);
                c.setRegion(region);

                c = service.saveCountry(c);
                log.debug("Saved new country in db: " + c);

                // now we source it
                Source source = new Source(c.getId(), SourceTypeCd.COUNTRY, c.getShortName(), null, null, null, null, new Date());
                source = sourcingService.saveSourceFromGTE(source);
            } else {
                log.debug("Country already exists: " + c);
            }

            // save cities for country
            Element citiesE = country.element("cities");

            if (citiesE != null) {
                List<Element> cities = citiesE.elements("city");

                if (cities != null) {
                    List<City> citys = new ArrayList<City>();

                    for (Element city : cities) {
                        citys.add(processCity(c, city));
                    }

                    c.setCities(citys);
                }
            }

            c = service.updateCountry(c);
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return c;
    }

    private City processCity(Country c, Element city) throws DataCreatorException {
        String code = city.attributeValue("code");
        String defaultLanguage = city.attributeValue("en_US");
        City theCity = null;

        try {
            theCity = service.getCityByCode(code);

            if (theCity == null) {
                theCity = new City();
                theCity.setCode(code);
                theCity.setShortName(defaultLanguage);
                theCity.setCountry(c);

                service.saveCity(theCity);
                log.debug("Saved new city in db: " + theCity);

                // now we source it
                Source source = new Source(theCity.getId(), SourceTypeCd.CITY, theCity.getShortName(), null, null, null, null, new Date());
                source = sourcingService.saveSourceFromGTE(source);
            } else {
                log.debug("City already exists: " + theCity);
            }

        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return theCity;
    }
}