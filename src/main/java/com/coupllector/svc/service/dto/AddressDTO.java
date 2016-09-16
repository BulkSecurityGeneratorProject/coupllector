package com.coupllector.svc.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String suburb;

    private String state;

    private String postcode;

    private String country;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;

        if ( ! Objects.equals(id, addressDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + id +
            ", addressLine1='" + addressLine1 + "'" +
            ", addressLine2='" + addressLine2 + "'" +
            ", suburb='" + suburb + "'" +
            ", state='" + state + "'" +
            ", postcode='" + postcode + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
