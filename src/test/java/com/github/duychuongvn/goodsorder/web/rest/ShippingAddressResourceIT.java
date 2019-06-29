package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.GoodsorderApp;
import com.github.duychuongvn.goodsorder.domain.ShippingAddress;
import com.github.duychuongvn.goodsorder.repository.ShippingAddressRepository;
import com.github.duychuongvn.goodsorder.service.ShippingAddressService;
import com.github.duychuongvn.goodsorder.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.github.duychuongvn.goodsorder.web.rest.TestUtil.sameInstant;
import static com.github.duychuongvn.goodsorder.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ShippingAddressResource} REST controller.
 */
@SpringBootTest(classes = GoodsorderApp.class)
public class ShippingAddressResourceIT {

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_ADDRESS = false;
    private static final Boolean UPDATED_DEFAULT_ADDRESS = true;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restShippingAddressMockMvc;

    private ShippingAddress shippingAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShippingAddressResource shippingAddressResource = new ShippingAddressResource(shippingAddressService);
        this.restShippingAddressMockMvc = MockMvcBuilders.standaloneSetup(shippingAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingAddress createEntity(EntityManager em) {
        ShippingAddress shippingAddress = new ShippingAddress()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .zipCode(DEFAULT_ZIP_CODE)
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .defaultAddress(DEFAULT_DEFAULT_ADDRESS)
            .createdAt(DEFAULT_CREATED_AT)
            .lastUpdatedAt(DEFAULT_LAST_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return shippingAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingAddress createUpdatedEntity(EntityManager em) {
        ShippingAddress shippingAddress = new ShippingAddress()
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .defaultAddress(UPDATED_DEFAULT_ADDRESS)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return shippingAddress;
    }

    @BeforeEach
    public void initTest() {
        shippingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingAddress() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress
        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isCreated());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingAddress testShippingAddress = shippingAddressList.get(shippingAddressList.size() - 1);
        assertThat(testShippingAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testShippingAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testShippingAddress.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testShippingAddress.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testShippingAddress.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testShippingAddress.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testShippingAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testShippingAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShippingAddress.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testShippingAddress.isDefaultAddress()).isEqualTo(DEFAULT_DEFAULT_ADDRESS);
        assertThat(testShippingAddress.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testShippingAddress.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testShippingAddress.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testShippingAddress.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createShippingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress with an existing ID
        shippingAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShippingAddresses() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].defaultAddress").value(hasItem(DEFAULT_DEFAULT_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", shippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.defaultAddress").value(DEFAULT_DEFAULT_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingAddress() throws Exception {
        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressService.save(shippingAddress);

        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Update the shippingAddress
        ShippingAddress updatedShippingAddress = shippingAddressRepository.findById(shippingAddress.getId()).get();
        // Disconnect from session so that the updates on updatedShippingAddress are not directly saved in db
        em.detach(updatedShippingAddress);
        updatedShippingAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .defaultAddress(UPDATED_DEFAULT_ADDRESS)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShippingAddress)))
            .andExpect(status().isOk());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeUpdate);
        ShippingAddress testShippingAddress = shippingAddressList.get(shippingAddressList.size() - 1);
        assertThat(testShippingAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testShippingAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testShippingAddress.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testShippingAddress.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testShippingAddress.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testShippingAddress.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testShippingAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testShippingAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShippingAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testShippingAddress.isDefaultAddress()).isEqualTo(UPDATED_DEFAULT_ADDRESS);
        assertThat(testShippingAddress.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testShippingAddress.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testShippingAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testShippingAddress.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingAddress() throws Exception {
        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressService.save(shippingAddress);

        int databaseSizeBeforeDelete = shippingAddressRepository.findAll().size();

        // Delete the shippingAddress
        restShippingAddressMockMvc.perform(delete("/api/shipping-addresses/{id}", shippingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingAddress.class);
        ShippingAddress shippingAddress1 = new ShippingAddress();
        shippingAddress1.setId(1L);
        ShippingAddress shippingAddress2 = new ShippingAddress();
        shippingAddress2.setId(shippingAddress1.getId());
        assertThat(shippingAddress1).isEqualTo(shippingAddress2);
        shippingAddress2.setId(2L);
        assertThat(shippingAddress1).isNotEqualTo(shippingAddress2);
        shippingAddress1.setId(null);
        assertThat(shippingAddress1).isNotEqualTo(shippingAddress2);
    }
}
