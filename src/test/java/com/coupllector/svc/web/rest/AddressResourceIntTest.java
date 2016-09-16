package com.coupllector.svc.web.rest;

import com.coupllector.svc.CoupllectorApp;
import com.coupllector.svc.domain.Address;
import com.coupllector.svc.repository.AddressRepository;
import com.coupllector.svc.service.AddressService;
import com.coupllector.svc.service.dto.AddressDTO;
import com.coupllector.svc.service.mapper.AddressMapper;

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
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoupllectorApp.class)
public class AddressResourceIntTest {
    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBB";
    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBB";
    private static final String DEFAULT_SUBURB = "AAAAA";
    private static final String UPDATED_SUBURB = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_POSTCODE = "AAAAA";
    private static final String UPDATED_POSTCODE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private AddressMapper addressMapper;

    @Inject
    private AddressService addressService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAddressMockMvc;

    private Address address;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressResource addressResource = new AddressResource();
        ReflectionTestUtils.setField(addressResource, "addressService", addressService);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address();
        address = new Address()
                .addressLine1(DEFAULT_ADDRESS_LINE_1)
                .addressLine2(DEFAULT_ADDRESS_LINE_2)
                .suburb(DEFAULT_SUBURB)
                .state(DEFAULT_STATE)
                .postcode(DEFAULT_POSTCODE)
                .country(DEFAULT_COUNTRY);
        return address;
    }

    @Before
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);

        restAddressMockMvc.perform(post("/api/addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
                .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addresses = addressRepository.findAll();
        assertThat(addresses).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addresses.get(addresses.size() - 1);
        assertThat(testAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testAddress.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addresses
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
                .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
                .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
                .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findOne(address.getId());
        updatedAddress
                .addressLine1(UPDATED_ADDRESS_LINE_1)
                .addressLine2(UPDATED_ADDRESS_LINE_2)
                .suburb(UPDATED_SUBURB)
                .state(UPDATED_STATE)
                .postcode(UPDATED_POSTCODE)
                .country(UPDATED_COUNTRY);
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
                .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addresses = addressRepository.findAll();
        assertThat(addresses).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addresses.get(addresses.size() - 1);
        assertThat(testAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testAddress.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addresses = addressRepository.findAll();
        assertThat(addresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
