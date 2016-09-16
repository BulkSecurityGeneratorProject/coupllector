package com.coupllector.svc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "primary_email_address")
    private String primaryEmailAddress;

    @Column(name = "alternative_email_address")
    private String alternativeEmailAddress;

    @Column(name = "reward_points")
    private Long rewardPoints;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Address streetAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Address postalAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public UserProfile mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public UserProfile primaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
        return this;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }

    public String getAlternativeEmailAddress() {
        return alternativeEmailAddress;
    }

    public UserProfile alternativeEmailAddress(String alternativeEmailAddress) {
        this.alternativeEmailAddress = alternativeEmailAddress;
        return this;
    }

    public void setAlternativeEmailAddress(String alternativeEmailAddress) {
        this.alternativeEmailAddress = alternativeEmailAddress;
    }

    public Long getRewardPoints() {
        return rewardPoints;
    }

    public UserProfile rewardPoints(Long rewardPoints) {
        this.rewardPoints = rewardPoints;
        return this;
    }

    public void setRewardPoints(Long rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public User getUser() {
        return user;
    }

    public UserProfile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getStreetAddress() {
        return streetAddress;
    }

    public UserProfile streetAddress(Address address) {
        this.streetAddress = address;
        return this;
    }

    public void setStreetAddress(Address address) {
        this.streetAddress = address;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public UserProfile postalAddress(Address address) {
        this.postalAddress = address;
        return this;
    }

    public void setPostalAddress(Address address) {
        this.postalAddress = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile userProfile = (UserProfile) o;
        if(userProfile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userProfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + id +
            ", mobile='" + mobile + "'" +
            ", primaryEmailAddress='" + primaryEmailAddress + "'" +
            ", alternativeEmailAddress='" + alternativeEmailAddress + "'" +
            ", rewardPoints='" + rewardPoints + "'" +
            '}';
    }
}
