package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.GoodsorderApp;
import com.github.duychuongvn.goodsorder.domain.OrderTracking;
import com.github.duychuongvn.goodsorder.repository.OrderTrackingRepository;
import com.github.duychuongvn.goodsorder.service.OrderTrackingService;
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

import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;
/**
 * Integration tests for the {@Link OrderTrackingResource} REST controller.
 */
@SpringBootTest(classes = GoodsorderApp.class)
public class OrderTrackingResourceIT {

    private static final DeliveryStatus DEFAULT_DELIVERY_STATUS = DeliveryStatus.INIT;
    private static final DeliveryStatus UPDATED_DELIVERY_STATUS = DeliveryStatus.PICKED_UP;

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Autowired
    private OrderTrackingService orderTrackingService;

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

    private MockMvc restOrderTrackingMockMvc;

    private OrderTracking orderTracking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderTrackingResource orderTrackingResource = new OrderTrackingResource(orderTrackingService);
        this.restOrderTrackingMockMvc = MockMvcBuilders.standaloneSetup(orderTrackingResource)
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
    public static OrderTracking createEntity(EntityManager em) {
        OrderTracking orderTracking = new OrderTracking()
            .deliveryStatus(DEFAULT_DELIVERY_STATUS)
            .dateTime(DEFAULT_DATE_TIME)
            .remark(DEFAULT_REMARK)
            .createdAt(DEFAULT_CREATED_AT)
            .lastUpdatedAt(DEFAULT_LAST_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return orderTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTracking createUpdatedEntity(EntityManager em) {
        OrderTracking orderTracking = new OrderTracking()
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .dateTime(UPDATED_DATE_TIME)
            .remark(UPDATED_REMARK)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return orderTracking;
    }

    @BeforeEach
    public void initTest() {
        orderTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTracking() throws Exception {
        int databaseSizeBeforeCreate = orderTrackingRepository.findAll().size();

        // Create the OrderTracking
        restOrderTrackingMockMvc.perform(post("/api/order-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTracking)))
            .andExpect(status().isCreated());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTracking testOrderTracking = orderTrackingList.get(orderTrackingList.size() - 1);
        assertThat(testOrderTracking.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
        assertThat(testOrderTracking.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testOrderTracking.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testOrderTracking.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrderTracking.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testOrderTracking.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrderTracking.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createOrderTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTrackingRepository.findAll().size();

        // Create the OrderTracking with an existing ID
        orderTracking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTrackingMockMvc.perform(post("/api/order-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTracking)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderTrackings() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList
        restOrderTrackingMockMvc.perform(get("/api/order-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderTracking() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get the orderTracking
        restOrderTrackingMockMvc.perform(get("/api/order-trackings/{id}", orderTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderTracking.getId().intValue()))
            .andExpect(jsonPath("$.deliveryStatus").value(DEFAULT_DELIVERY_STATUS.toString()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderTracking() throws Exception {
        // Get the orderTracking
        restOrderTrackingMockMvc.perform(get("/api/order-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTracking() throws Exception {
        // Initialize the database
        orderTrackingService.save(orderTracking);

        int databaseSizeBeforeUpdate = orderTrackingRepository.findAll().size();

        // Update the orderTracking
        OrderTracking updatedOrderTracking = orderTrackingRepository.findById(orderTracking.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTracking are not directly saved in db
        em.detach(updatedOrderTracking);
        updatedOrderTracking
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .dateTime(UPDATED_DATE_TIME)
            .remark(UPDATED_REMARK)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restOrderTrackingMockMvc.perform(put("/api/order-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderTracking)))
            .andExpect(status().isOk());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeUpdate);
        OrderTracking testOrderTracking = orderTrackingList.get(orderTrackingList.size() - 1);
        assertThat(testOrderTracking.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
        assertThat(testOrderTracking.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testOrderTracking.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testOrderTracking.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrderTracking.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testOrderTracking.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrderTracking.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTracking() throws Exception {
        int databaseSizeBeforeUpdate = orderTrackingRepository.findAll().size();

        // Create the OrderTracking

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTrackingMockMvc.perform(put("/api/order-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTracking)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTracking() throws Exception {
        // Initialize the database
        orderTrackingService.save(orderTracking);

        int databaseSizeBeforeDelete = orderTrackingRepository.findAll().size();

        // Delete the orderTracking
        restOrderTrackingMockMvc.perform(delete("/api/order-trackings/{id}", orderTracking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTracking.class);
        OrderTracking orderTracking1 = new OrderTracking();
        orderTracking1.setId(1L);
        OrderTracking orderTracking2 = new OrderTracking();
        orderTracking2.setId(orderTracking1.getId());
        assertThat(orderTracking1).isEqualTo(orderTracking2);
        orderTracking2.setId(2L);
        assertThat(orderTracking1).isNotEqualTo(orderTracking2);
        orderTracking1.setId(null);
        assertThat(orderTracking1).isNotEqualTo(orderTracking2);
    }
}
