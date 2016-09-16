package com.coupllector.svc.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the RewardPointHistory entity.
 */
public class RewardPointHistoryDTO implements Serializable {

    private Long id;

    private ZonedDateTime changedDate;

    private Long changedValue;

    private Long balance;

    private String transaction;

    private String comment;


    private Long userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(ZonedDateTime changedDate) {
        this.changedDate = changedDate;
    }
    public Long getChangedValue() {
        return changedValue;
    }

    public void setChangedValue(Long changedValue) {
        this.changedValue = changedValue;
    }
    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RewardPointHistoryDTO rewardPointHistoryDTO = (RewardPointHistoryDTO) o;

        if ( ! Objects.equals(id, rewardPointHistoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RewardPointHistoryDTO{" +
            "id=" + id +
            ", changedDate='" + changedDate + "'" +
            ", changedValue='" + changedValue + "'" +
            ", balance='" + balance + "'" +
            ", transaction='" + transaction + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
