package com.coupllector.svc.service.impl;

import com.coupllector.svc.service.AddressService;
import com.coupllector.svc.domain.Address;
import com.coupllector.svc.repository.AddressRepository;
import com.coupllector.svc.service.dto.AddressDTO;
import com.coupllector.svc.service.mapper.AddressMapper;
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
 * Service Implementation for managing Address.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    
    @Inject
    private AddressRepository addressRepository;

    @Inject
    private AddressMapper addressMapper;

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        address = addressRepository.save(address);
        AddressDTO result = addressMapper.addressToAddressDTO(address);
        return result;
    }

    /**
     *  Get all the addresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        Page<Address> result = addressRepository.findAll(pageable);
        return result.map(address -> addressMapper.addressToAddressDTO(address));
    }

    /**
     *  Get one address by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AddressDTO findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);
        return addressDTO;
    }

    /**
     *  Delete the  address by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
    }
}
