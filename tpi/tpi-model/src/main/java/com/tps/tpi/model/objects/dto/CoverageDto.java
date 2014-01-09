package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:37:01 PM
 * Responsibility:
 */
public class CoverageDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1014L;
    private String history;
    private CompanyDto company;
    private ProductDto product;
    private SkilledRoleDto skilledRole;
    private RegionDto region;
    private List<CoverageEndorsementDto> coverageEndorsements;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public SkilledRoleDto getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRoleDto skilledRole) {
        this.skilledRole = skilledRole;
    }

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }

    public List<CoverageEndorsementDto> getCoverageEndorsements() {
        return coverageEndorsements;
    }

    public void setCoverageEndorsements(List<CoverageEndorsementDto> coverageEndorsements) {
        this.coverageEndorsements = coverageEndorsements;
    }

    public void addCoverageEndorsement(CoverageEndorsementDto dto) {
        if (coverageEndorsements == null) {
            coverageEndorsements = new ArrayList<CoverageEndorsementDto>();
        }

        boolean isNew = true;
        for (CoverageEndorsementDto aDto : coverageEndorsements) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            coverageEndorsements.add(dto);
        }
        
    }
}
