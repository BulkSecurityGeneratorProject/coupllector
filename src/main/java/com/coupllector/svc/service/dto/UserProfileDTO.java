package com.coupllector.svc.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the UserProfile entity.
 */
public class UserProfileDTO implements Serializable {

    private Long id;

    private String mobile;

    private String primaryEmailAddress;

    private String alternativeEmailAddress;

    private Long rewardPoints;


    private Long userId;
    
    private Long streetAddressId;
    
    private Long postalAddressId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }
    public String getAlternativeEmailAddress() {
        return alternativeEmailAddress;
    }

    public void setAlternativeEmailAddress(String alternativeEmailAddress) {
        this.alternativeEmailAddress = alternativeEmailAddress;
    }
    public Long getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Long rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStreetAddressId() {
        return streetAddressId;
    }

    public void setStreetAddressId(Long addressId) {
        this.streetAddressId = addressId;
    }

    public Long getPostalAddressId() {
        return postalAddressId;
    }

    public void setPostalAddressId(Long addressId) {
        this.postalAddressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserProfileDTO userProfileDTO = (UserProfileDTO) o;

        if ( ! Objects.equals(id, userProfileDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
            "id=" + id +
            ", mobile='" + mobile + "'" +
            ", primaryEmailAddress='" + primaryEmailAddress + "'" +
            ", alternativeEmailAddress='" + alternativeEmailAddress + "'" +
            ", rewardPoints='" + rewardPoints + "'" +
            '}';
    }
}
