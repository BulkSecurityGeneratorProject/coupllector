package com.coupllector.svc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coupllector.svc.service.RewardPointHistoryService;
import com.coupllector.svc.web.rest.util.HeaderUtil;
import com.coupllector.svc.web.rest.util.PaginationUtil;
import com.coupllector.svc.service.dto.RewardPointHistoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing RewardPointHistory.
 */
@RestController
@RequestMapping("/api")
public class RewardPointHistoryResource {

    private final Logger log = LoggerFactory.getLogger(RewardPointHistoryResource.class);
        
    @Inject
    private RewardPointHistoryService rewardPointHistoryService;

    /**
     * POST  /reward-point-histories : Create a new rewardPointHistory.
     *
     * @param rewardPointHistoryDTO the rewardPointHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rewardPointHistoryDTO, or with status 400 (Bad Request) if the rewardPointHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/reward-point-histories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RewardPointHistoryDTO> createRewardPointHistory(@RequestBody RewardPointHistoryDTO rewardPointHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save RewardPointHistory : {}", rewardPointHistoryDTO);
        if (rewardPointHistoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rewardPointHistory", "idexists", "A new rewardPointHistory cannot already have an ID")).body(null);
        }
        RewardPointHistoryDTO result = rewardPointHistoryService.save(rewardPointHistoryDTO);
        return ResponseEntity.created(new URI("/api/reward-point-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rewardPointHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reward-point-histories : Updates an existing rewardPointHistory.
     *
     * @param rewardPointHistoryDTO the rewardPointHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rewardPointHistoryDTO,
     * or with status 400 (Bad Request) if the rewardPointHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the rewardPointHistoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/reward-point-histories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RewardPointHistoryDTO> updateRewardPointHistory(@RequestBody RewardPointHistoryDTO rewardPointHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update RewardPointHistory : {}", rewardPointHistoryDTO);
        if (rewardPointHistoryDTO.getId() == null) {
            return createRewardPointHistory(rewardPointHistoryDTO);
        }
        RewardPointHistoryDTO result = rewardPointHistoryService.save(rewardPointHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rewardPointHistory", rewardPointHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reward-point-histories : get all the rewardPointHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rewardPointHistories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/reward-point-histories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RewardPointHistoryDTO>> getAllRewardPointHistories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RewardPointHistories");
        Page<RewardPointHistoryDTO> page = rewardPointHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reward-point-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reward-point-histories/:id : get the "id" rewardPointHistory.
     *
     * @param id the id of the rewardPointHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rewardPointHistoryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/reward-point-histories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RewardPointHistoryDTO> getRewardPointHistory(@PathVariable Long id) {
        log.debug("REST request to get RewardPointHistory : {}", id);
        RewardPointHistoryDTO rewardPointHistoryDTO = rewardPointHistoryService.findOne(id);
        return Optional.ofNullable(rewardPointHistoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reward-point-histories/:id : delete the "id" rewardPointHistory.
     *
     * @param id the id of the rewardPointHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/reward-point-histories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRewardPointHistory(@PathVariable Long id) {
        log.debug("REST request to delete RewardPointHistory : {}", id);
        rewardPointHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rewardPointHistory", id.toString())).build();
    }

}
