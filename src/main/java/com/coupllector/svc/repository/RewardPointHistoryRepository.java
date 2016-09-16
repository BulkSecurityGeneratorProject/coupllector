package com.coupllector.svc.repository;

import com.coupllector.svc.domain.RewardPointHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RewardPointHistory entity.
 */
@SuppressWarnings("unused")
public interface RewardPointHistoryRepository extends JpaRepository<RewardPointHistory,Long> {

    @Query("select rewardPointHistory from RewardPointHistory rewardPointHistory where rewardPointHistory.user.login = ?#{principal.username}")
    List<RewardPointHistory> findByUserIsCurrentUser();

}
