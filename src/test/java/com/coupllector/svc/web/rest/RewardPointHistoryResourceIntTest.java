package com.coupllector.svc.web.rest;

import com.coupllector.svc.CoupllectorApp;
import com.coupllector.svc.domain.RewardPointHistory;
import com.coupllector.svc.repository.RewardPointHistoryRepository;
import com.coupllector.svc.service.RewardPointHistoryService;
import com.coupllector.svc.service.dto.RewardPointHistoryDTO;
import com.coupllector.svc.service.mapper.RewardPointHistoryMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RewardPointHistoryResource REST controller.
 *
 * @see RewardPointHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoupllectorApp.class)
public class RewardPointHistoryResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_CHANGED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CHANGED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CHANGED_DATE_STR = dateTimeFormatter.format(DEFAULT_CHANGED_DATE);

    private static final Long DEFAULT_CHANGED_VALUE = 1L;
    private static final Long UPDATED_CHANGED_VALUE = 2L;

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;
    private static final String DEFAULT_TRANSACTION = "AAAAA";
    private static final String UPDATED_TRANSACTION = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private RewardPointHistoryRepository rewardPointHistoryRepository;

    @Inject
    private RewardPointHistoryMapper rewardPointHistoryMapper;

    @Inject
    private RewardPointHistoryService rewardPointHistoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRewardPointHistoryMockMvc;

    private RewardPointHistory rewardPointHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RewardPointHistoryResource rewardPointHistoryResource = new RewardPointHistoryResource();
        ReflectionTestUtils.setField(rewardPointHistoryResource, "rewardPointHistoryService", rewardPointHistoryService);
        this.restRewardPointHistoryMockMvc = MockMvcBuilders.standaloneSetup(rewardPointHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RewardPointHistory createEntity(EntityManager em) {
        RewardPointHistory rewardPointHistory = new RewardPointHistory();
        rewardPointHistory = new RewardPointHistory()
                .changedDate(DEFAULT_CHANGED_DATE)
                .changedValue(DEFAULT_CHANGED_VALUE)
                .balance(DEFAULT_BALANCE)
                .transaction(DEFAULT_TRANSACTION)
                .comment(DEFAULT_COMMENT);
        return rewardPointHistory;
    }

    @Before
    public void initTest() {
        rewardPointHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createRewardPointHistory() throws Exception {
        int databaseSizeBeforeCreate = rewardPointHistoryRepository.findAll().size();

        // Create the RewardPointHistory
        RewardPointHistoryDTO rewardPointHistoryDTO = rewardPointHistoryMapper.rewardPointHistoryToRewardPointHistoryDTO(rewardPointHistory);

        restRewardPointHistoryMockMvc.perform(post("/api/reward-point-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rewardPointHistoryDTO)))
                .andExpect(status().isCreated());

        // Validate the RewardPointHistory in the database
        List<RewardPointHistory> rewardPointHistories = rewardPointHistoryRepository.findAll();
        assertThat(rewardPointHistories).hasSize(databaseSizeBeforeCreate + 1);
        RewardPointHistory testRewardPointHistory = rewardPointHistories.get(rewardPointHistories.size() - 1);
        assertThat(testRewardPointHistory.getChangedDate()).isEqualTo(DEFAULT_CHANGED_DATE);
        assertThat(testRewardPointHistory.getChangedValue()).isEqualTo(DEFAULT_CHANGED_VALUE);
        assertThat(testRewardPointHistory.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testRewardPointHistory.getTransaction()).isEqualTo(DEFAULT_TRANSACTION);
        assertThat(testRewardPointHistory.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllRewardPointHistories() throws Exception {
        // Initialize the database
        rewardPointHistoryRepository.saveAndFlush(rewardPointHistory);

        // Get all the rewardPointHistories
        restRewardPointHistoryMockMvc.perform(get("/api/reward-point-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rewardPointHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].changedDate").value(hasItem(DEFAULT_CHANGED_DATE_STR)))
                .andExpect(jsonPath("$.[*].changedValue").value(hasItem(DEFAULT_CHANGED_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
                .andExpect(jsonPath("$.[*].transaction").value(hasItem(DEFAULT_TRANSACTION.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getRewardPointHistory() throws Exception {
        // Initialize the database
        rewardPointHistoryRepository.saveAndFlush(rewardPointHistory);

        // Get the rewardPointHistory
        restRewardPointHistoryMockMvc.perform(get("/api/reward-point-histories/{id}", rewardPointHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rewardPointHistory.getId().intValue()))
            .andExpect(jsonPath("$.changedDate").value(DEFAULT_CHANGED_DATE_STR))
            .andExpect(jsonPath("$.changedValue").value(DEFAULT_CHANGED_VALUE.intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.transaction").value(DEFAULT_TRANSACTION.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRewardPointHistory() throws Exception {
        // Get the rewardPointHistory
        restRewardPointHistoryMockMvc.perform(get("/api/reward-point-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRewardPointHistory() throws Exception {
        // Initialize the database
        rewardPointHistoryRepository.saveAndFlush(rewardPointHistory);
        int databaseSizeBeforeUpdate = rewardPointHistoryRepository.findAll().size();

        // Update the rewardPointHistory
        RewardPointHistory updatedRewardPointHistory = rewardPointHistoryRepository.findOne(rewardPointHistory.getId());
        updatedRewardPointHistory
                .changedDate(UPDATED_CHANGED_DATE)
                .changedValue(UPDATED_CHANGED_VALUE)
                .balance(UPDATED_BALANCE)
                .transaction(UPDATED_TRANSACTION)
                .comment(UPDATED_COMMENT);
        RewardPointHistoryDTO rewardPointHistoryDTO = rewardPointHistoryMapper.rewardPointHistoryToRewardPointHistoryDTO(updatedRewardPointHistory);

        restRewardPointHistoryMockMvc.perform(put("/api/reward-point-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rewardPointHistoryDTO)))
                .andExpect(status().isOk());

        // Validate the RewardPointHistory in the database
        List<RewardPointHistory> rewardPointHistories = rewardPointHistoryRepository.findAll();
        assertThat(rewardPointHistories).hasSize(databaseSizeBeforeUpdate);
        RewardPointHistory testRewardPointHistory = rewardPointHistories.get(rewardPointHistories.size() - 1);
        assertThat(testRewardPointHistory.getChangedDate()).isEqualTo(UPDATED_CHANGED_DATE);
        assertThat(testRewardPointHistory.getChangedValue()).isEqualTo(UPDATED_CHANGED_VALUE);
        assertThat(testRewardPointHistory.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testRewardPointHistory.getTransaction()).isEqualTo(UPDATED_TRANSACTION);
        assertThat(testRewardPointHistory.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteRewardPointHistory() throws Exception {
        // Initialize the database
        rewardPointHistoryRepository.saveAndFlush(rewardPointHistory);
        int databaseSizeBeforeDelete = rewardPointHistoryRepository.findAll().size();

        // Get the rewardPointHistory
        restRewardPointHistoryMockMvc.perform(delete("/api/reward-point-histories/{id}", rewardPointHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RewardPointHistory> rewardPointHistories = rewardPointHistoryRepository.findAll();
        assertThat(rewardPointHistories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
