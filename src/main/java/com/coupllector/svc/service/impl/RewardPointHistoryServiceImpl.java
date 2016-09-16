package com.coupllector.svc.service.impl;

import com.coupllector.svc.service.RewardPointHistoryService;
import com.coupllector.svc.domain.RewardPointHistory;
import com.coupllector.svc.repository.RewardPointHistoryRepository;
import com.coupllector.svc.service.dto.RewardPointHistoryDTO;
import com.coupllector.svc.service.mapper.RewardPointHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RewardPointHistory.
 */
@Service
@Transactional
public class RewardPointHistoryServiceImpl implements RewardPointHistoryService{

    private final Logger log = LoggerFactory.getLogger(RewardPointHistoryServiceImpl.class);
    
    @Inject
    private RewardPointHistoryRepository rewardPointHistoryRepository;

    @Inject
    private RewardPointHistoryMapper rewardPointHistoryMapper;

    /**
     * Save a rewardPointHistory.
     *
     * @param rewardPointHistoryDTO the entity to save
     * @return the persisted entity
     */
    public RewardPointHistoryDTO save(RewardPointHistoryDTO rewardPointHistoryDTO) {
        log.debug("Request to save RewardPointHistory : {}", rewardPointHistoryDTO);
        RewardPointHistory rewardPointHistory = rewardPointHistoryMapper.rewardPointHistoryDTOToRewardPointHistory(rewardPointHistoryDTO);
        rewardPointHistory = rewardPointHistoryRepository.save(rewardPointHistory);
        RewardPointHistoryDTO result = rewardPointHistoryMapper.rewardPointHistoryToRewardPointHistoryDTO(rewardPointHistory);
        return result;
    }

    /**
     *  Get all the rewardPointHistories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<RewardPointHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RewardPointHistories");
        Page<RewardPointHistory> result = rewardPointHistoryRepository.findAll(pageable);
        return result.map(rewardPointHistory -> rewardPointHistoryMapper.rewardPointHistoryToRewardPointHistoryDTO(rewardPointHistory));
    }

    /**
     *  Get one rewardPointHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RewardPointHistoryDTO findOne(Long id) {
        log.debug("Request to get RewardPointHistory : {}", id);
        RewardPointHistory rewardPointHistory = rewardPointHistoryRepository.findOne(id);
        RewardPointHistoryDTO rewardPointHistoryDTO = rewardPointHistoryMapper.rewardPointHistoryToRewardPointHistoryDTO(rewardPointHistory);
        return rewardPointHistoryDTO;
    }

    /**
     *  Delete the  rewardPointHistory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RewardPointHistory : {}", id);
        rewardPointHistoryRepository.delete(id);
    }
}
