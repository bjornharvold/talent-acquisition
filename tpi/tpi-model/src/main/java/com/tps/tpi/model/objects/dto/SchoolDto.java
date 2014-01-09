package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 12:03:18 PM
 * Responsibility:
 */
public class SchoolDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1036L;
    private AddressDto address;

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
