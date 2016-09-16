package com.coupllector.svc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RewardPointHistory.
 */
@Entity
@Table(name = "reward_point_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RewardPointHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "changed_date")
    private ZonedDateTime changedDate;

    @Column(name = "changed_value")
    private Long changedValue;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "transaction")
    private String transaction;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getChangedDate() {
        return changedDate;
    }

    public RewardPointHistory changedDate(ZonedDateTime changedDate) {
        this.changedDate = changedDate;
        return this;
    }

    public void setChangedDate(ZonedDateTime changedDate) {
        this.changedDate = changedDate;
    }

    public Long getChangedValue() {
        return changedValue;
    }

    public RewardPointHistory changedValue(Long changedValue) {
        this.changedValue = changedValue;
        return this;
    }

    public void setChangedValue(Long changedValue) {
        this.changedValue = changedValue;
    }

    public Long getBalance() {
        return balance;
    }

    public RewardPointHistory balance(Long balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getTransaction() {
        return transaction;
    }

    public RewardPointHistory transaction(String transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getComment() {
        return comment;
    }

    public RewardPointHistory comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public RewardPointHistory user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RewardPointHistory rewardPointHistory = (RewardPointHistory) o;
        if(rewardPointHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rewardPointHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RewardPointHistory{" +
            "id=" + id +
            ", changedDate='" + changedDate + "'" +
            ", changedValue='" + changedValue + "'" +
            ", balance='" + balance + "'" +
            ", transaction='" + transaction + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
