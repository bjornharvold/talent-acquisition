package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 12:52:03 PM
 * Responsibility:
 */
public class PersonAddressDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1026L;
    private String person;
    private AddressDto address;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
