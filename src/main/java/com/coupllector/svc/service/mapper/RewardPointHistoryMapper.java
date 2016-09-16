package com.coupllector.svc.service.mapper;

import com.coupllector.svc.domain.*;
import com.coupllector.svc.service.dto.RewardPointHistoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity RewardPointHistory and its DTO RewardPointHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface RewardPointHistoryMapper {

    @Mapping(source = "user.id", target = "userId")
    RewardPointHistoryDTO rewardPointHistoryToRewardPointHistoryDTO(RewardPointHistory rewardPointHistory);

    List<RewardPointHistoryDTO> rewardPointHistoriesToRewardPointHistoryDTOs(List<RewardPointHistory> rewardPointHistories);

    @Mapping(source = "userId", target = "user")
    RewardPointHistory rewardPointHistoryDTOToRewardPointHistory(RewardPointHistoryDTO rewardPointHistoryDTO);

    List<RewardPointHistory> rewardPointHistoryDTOsToRewardPointHistories(List<RewardPointHistoryDTO> rewardPointHistoryDTOs);
}
