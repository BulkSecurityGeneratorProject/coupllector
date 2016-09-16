package com.coupllector.svc.service;

import com.coupllector.svc.service.dto.RewardPointHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing RewardPointHistory.
 */
public interface RewardPointHistoryService {

    /**
     * Save a rewardPointHistory.
     *
     * @param rewardPointHistoryDTO the entity to save
     * @return the persisted entity
     */
    RewardPointHistoryDTO save(RewardPointHistoryDTO rewardPointHistoryDTO);

    /**
     *  Get all the rewardPointHistories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RewardPointHistoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" rewardPointHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RewardPointHistoryDTO findOne(Long id);

    /**
     *  Delete the "id" rewardPointHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
