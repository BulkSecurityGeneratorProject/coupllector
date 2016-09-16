package com.coupllector.svc.web.rest;

import com.coupllector.svc.CoupllectorApp;
import com.coupllector.svc.domain.UserProfile;
import com.coupllector.svc.repository.UserProfileRepository;
import com.coupllector.svc.service.UserProfileService;
import com.coupllector.svc.service.dto.UserProfileDTO;
import com.coupllector.svc.service.mapper.UserProfileMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserProfileResource REST controller.
 *
 * @see UserProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoupllectorApp.class)
public class UserProfileResourceIntTest {
    private static final String DEFAULT_MOBILE = "AAAAA";
    private static final String UPDATED_MOBILE = "BBBBB";
    private static final String DEFAULT_PRIMARY_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_PRIMARY_EMAIL_ADDRESS = "BBBBB";
    private static final String DEFAULT_ALTERNATIVE_EMAIL_ADDRESS = "AAAAA";
    private static final String UPDATED_ALTERNATIVE_EMAIL_ADDRESS = "BBBBB";

    private static final Long DEFAULT_REWARD_POINTS = 1L;
    private static final Long UPDATED_REWARD_POINTS = 2L;

    @Inject
    private UserProfileRepository userProfileRepository;

    @Inject
    private UserProfileMapper userProfileMapper;

    @Inject
    private UserProfileService userProfileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserProfileResource userProfileResource = new UserProfileResource();
        ReflectionTestUtils.setField(userProfileResource, "userProfileService", userProfileService);
        this.restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile();
        userProfile = new UserProfile()
                .mobile(DEFAULT_MOBILE)
                .primaryEmailAddress(DEFAULT_PRIMARY_EMAIL_ADDRESS)
                .alternativeEmailAddress(DEFAULT_ALTERNATIVE_EMAIL_ADDRESS)
                .rewardPoints(DEFAULT_REWARD_POINTS);
        return userProfile;
    }

    @Before
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.userProfileToUserProfileDTO(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
                .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfiles.get(userProfiles.size() - 1);
        assertThat(testUserProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserProfile.getPrimaryEmailAddress()).isEqualTo(DEFAULT_PRIMARY_EMAIL_ADDRESS);
        assertThat(testUserProfile.getAlternativeEmailAddress()).isEqualTo(DEFAULT_ALTERNATIVE_EMAIL_ADDRESS);
        assertThat(testUserProfile.getRewardPoints()).isEqualTo(DEFAULT_REWARD_POINTS);
    }

    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfiles
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].primaryEmailAddress").value(hasItem(DEFAULT_PRIMARY_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].alternativeEmailAddress").value(hasItem(DEFAULT_ALTERNATIVE_EMAIL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].rewardPoints").value(hasItem(DEFAULT_REWARD_POINTS.intValue())));
    }

    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.primaryEmailAddress").value(DEFAULT_PRIMARY_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.alternativeEmailAddress").value(DEFAULT_ALTERNATIVE_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.rewardPoints").value(DEFAULT_REWARD_POINTS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findOne(userProfile.getId());
        updatedUserProfile
                .mobile(UPDATED_MOBILE)
                .primaryEmailAddress(UPDATED_PRIMARY_EMAIL_ADDRESS)
                .alternativeEmailAddress(UPDATED_ALTERNATIVE_EMAIL_ADDRESS)
                .rewardPoints(UPDATED_REWARD_POINTS);
        UserProfileDTO userProfileDTO = userProfileMapper.userProfileToUserProfileDTO(updatedUserProfile);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
                .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfiles.get(userProfiles.size() - 1);
        assertThat(testUserProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserProfile.getPrimaryEmailAddress()).isEqualTo(UPDATED_PRIMARY_EMAIL_ADDRESS);
        assertThat(testUserProfile.getAlternativeEmailAddress()).isEqualTo(UPDATED_ALTERNATIVE_EMAIL_ADDRESS);
        assertThat(testUserProfile.getRewardPoints()).isEqualTo(UPDATED_REWARD_POINTS);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Get the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        assertThat(userProfiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
