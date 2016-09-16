package com.coupllector.svc.service.impl;

import com.coupllector.svc.service.UserProfileService;
import com.coupllector.svc.domain.UserProfile;
import com.coupllector.svc.repository.UserProfileRepository;
import com.coupllector.svc.service.dto.UserProfileDTO;
import com.coupllector.svc.service.mapper.UserProfileMapper;
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
 * Service Implementation for managing UserProfile.
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService{

    private final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    
    @Inject
    private UserProfileRepository userProfileRepository;

    @Inject
    private UserProfileMapper userProfileMapper;

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    public UserProfileDTO save(UserProfileDTO userProfileDTO) {
        log.debug("Request to save UserProfile : {}", userProfileDTO);
        UserProfile userProfile = userProfileMapper.userProfileDTOToUserProfile(userProfileDTO);
        userProfile = userProfileRepository.save(userProfile);
        UserProfileDTO result = userProfileMapper.userProfileToUserProfileDTO(userProfile);
        return result;
    }

    /**
     *  Get all the userProfiles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserProfiles");
        Page<UserProfile> result = userProfileRepository.findAll(pageable);
        return result.map(userProfile -> userProfileMapper.userProfileToUserProfileDTO(userProfile));
    }

    /**
     *  Get one userProfile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserProfileDTO findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        UserProfile userProfile = userProfileRepository.findOne(id);
        UserProfileDTO userProfileDTO = userProfileMapper.userProfileToUserProfileDTO(userProfile);
        return userProfileDTO;
    }

    /**
     *  Delete the  userProfile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
    }
}
