package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.GoodsorderApp;
import com.github.duychuongvn.goodsorder.domain.OrderSchedule;
import com.github.duychuongvn.goodsorder.repository.OrderScheduleRepository;
import com.github.duychuongvn.goodsorder.service.OrderScheduleService;
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
import java.time.LocalDate;
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

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderScheduleStatus;
/**
 * Integration tests for the {@Link OrderScheduleResource} REST controller.
 */
@SpringBootTest(classes = GoodsorderApp.class)
public class OrderScheduleResourceIT {

    private static final LocalDate DEFAULT_OPEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPECTED_PACKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_PACKING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPECTED_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MAX_ORDER_NUMBER = 1;
    private static final Integer UPDATED_MAX_ORDER_NUMBER = 2;

    private static final Integer DEFAULT_CURRENT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_CURRENT_ORDER_NUMBER = 2;

    private static final OrderScheduleStatus DEFAULT_STATUS = OrderScheduleStatus.OPEN;
    private static final OrderScheduleStatus UPDATED_STATUS = OrderScheduleStatus.CLOSED;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private OrderScheduleRepository orderScheduleRepository;

    @Autowired
    private OrderScheduleService orderScheduleService;

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

    private MockMvc restOrderScheduleMockMvc;

    private OrderSchedule orderSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderScheduleResource orderScheduleResource = new OrderScheduleResource(orderScheduleService);
        this.restOrderScheduleMockMvc = MockMvcBuilders.standaloneSetup(orderScheduleResource)
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
    public static OrderSchedule createEntity(EntityManager em) {
        OrderSchedule orderSchedule = new OrderSchedule()
            .openDate(DEFAULT_OPEN_DATE)
            .closeDate(DEFAULT_CLOSE_DATE)
            .expectedPackingDate(DEFAULT_EXPECTED_PACKING_DATE)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .maxOrderNumber(DEFAULT_MAX_ORDER_NUMBER)
            .currentOrderNumber(DEFAULT_CURRENT_ORDER_NUMBER)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .lastUpdatedAt(DEFAULT_LAST_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return orderSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderSchedule createUpdatedEntity(EntityManager em) {
        OrderSchedule orderSchedule = new OrderSchedule()
            .openDate(UPDATED_OPEN_DATE)
            .closeDate(UPDATED_CLOSE_DATE)
            .expectedPackingDate(UPDATED_EXPECTED_PACKING_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .maxOrderNumber(UPDATED_MAX_ORDER_NUMBER)
            .currentOrderNumber(UPDATED_CURRENT_ORDER_NUMBER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return orderSchedule;
    }

    @BeforeEach
    public void initTest() {
        orderSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderSchedule() throws Exception {
        int databaseSizeBeforeCreate = orderScheduleRepository.findAll().size();

        // Create the OrderSchedule
        restOrderScheduleMockMvc.perform(post("/api/order-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderSchedule)))
            .andExpect(status().isCreated());

        // Validate the OrderSchedule in the database
        List<OrderSchedule> orderScheduleList = orderScheduleRepository.findAll();
        assertThat(orderScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        OrderSchedule testOrderSchedule = orderScheduleList.get(orderScheduleList.size() - 1);
        assertThat(testOrderSchedule.getOpenDate()).isEqualTo(DEFAULT_OPEN_DATE);
        assertThat(testOrderSchedule.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testOrderSchedule.getExpectedPackingDate()).isEqualTo(DEFAULT_EXPECTED_PACKING_DATE);
        assertThat(testOrderSchedule.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testOrderSchedule.getMaxOrderNumber()).isEqualTo(DEFAULT_MAX_ORDER_NUMBER);
        assertThat(testOrderSchedule.getCurrentOrderNumber()).isEqualTo(DEFAULT_CURRENT_ORDER_NUMBER);
        assertThat(testOrderSchedule.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderSchedule.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrderSchedule.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testOrderSchedule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrderSchedule.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createOrderScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderScheduleRepository.findAll().size();

        // Create the OrderSchedule with an existing ID
        orderSchedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderScheduleMockMvc.perform(post("/api/order-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the OrderSchedule in the database
        List<OrderSchedule> orderScheduleList = orderScheduleRepository.findAll();
        assertThat(orderScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderSchedules() throws Exception {
        // Initialize the database
        orderScheduleRepository.saveAndFlush(orderSchedule);

        // Get all the orderScheduleList
        restOrderScheduleMockMvc.perform(get("/api/order-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].openDate").value(hasItem(DEFAULT_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedPackingDate").value(hasItem(DEFAULT_EXPECTED_PACKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].maxOrderNumber").value(hasItem(DEFAULT_MAX_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].currentOrderNumber").value(hasItem(DEFAULT_CURRENT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderSchedule() throws Exception {
        // Initialize the database
        orderScheduleRepository.saveAndFlush(orderSchedule);

        // Get the orderSchedule
        restOrderScheduleMockMvc.perform(get("/api/order-schedules/{id}", orderSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderSchedule.getId().intValue()))
            .andExpect(jsonPath("$.openDate").value(DEFAULT_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.closeDate").value(DEFAULT_CLOSE_DATE.toString()))
            .andExpect(jsonPath("$.expectedPackingDate").value(DEFAULT_EXPECTED_PACKING_DATE.toString()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.maxOrderNumber").value(DEFAULT_MAX_ORDER_NUMBER))
            .andExpect(jsonPath("$.currentOrderNumber").value(DEFAULT_CURRENT_ORDER_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderSchedule() throws Exception {
        // Get the orderSchedule
        restOrderScheduleMockMvc.perform(get("/api/order-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderSchedule() throws Exception {
        // Initialize the database
        orderScheduleService.save(orderSchedule);

        int databaseSizeBeforeUpdate = orderScheduleRepository.findAll().size();

        // Update the orderSchedule
        OrderSchedule updatedOrderSchedule = orderScheduleRepository.findById(orderSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedOrderSchedule are not directly saved in db
        em.detach(updatedOrderSchedule);
        updatedOrderSchedule
            .openDate(UPDATED_OPEN_DATE)
            .closeDate(UPDATED_CLOSE_DATE)
            .expectedPackingDate(UPDATED_EXPECTED_PACKING_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .maxOrderNumber(UPDATED_MAX_ORDER_NUMBER)
            .currentOrderNumber(UPDATED_CURRENT_ORDER_NUMBER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restOrderScheduleMockMvc.perform(put("/api/order-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderSchedule)))
            .andExpect(status().isOk());

        // Validate the OrderSchedule in the database
        List<OrderSchedule> orderScheduleList = orderScheduleRepository.findAll();
        assertThat(orderScheduleList).hasSize(databaseSizeBeforeUpdate);
        OrderSchedule testOrderSchedule = orderScheduleList.get(orderScheduleList.size() - 1);
        assertThat(testOrderSchedule.getOpenDate()).isEqualTo(UPDATED_OPEN_DATE);
        assertThat(testOrderSchedule.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testOrderSchedule.getExpectedPackingDate()).isEqualTo(UPDATED_EXPECTED_PACKING_DATE);
        assertThat(testOrderSchedule.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testOrderSchedule.getMaxOrderNumber()).isEqualTo(UPDATED_MAX_ORDER_NUMBER);
        assertThat(testOrderSchedule.getCurrentOrderNumber()).isEqualTo(UPDATED_CURRENT_ORDER_NUMBER);
        assertThat(testOrderSchedule.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderSchedule.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrderSchedule.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testOrderSchedule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrderSchedule.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderSchedule() throws Exception {
        int databaseSizeBeforeUpdate = orderScheduleRepository.findAll().size();

        // Create the OrderSchedule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderScheduleMockMvc.perform(put("/api/order-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the OrderSchedule in the database
        List<OrderSchedule> orderScheduleList = orderScheduleRepository.findAll();
        assertThat(orderScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderSchedule() throws Exception {
        // Initialize the database
        orderScheduleService.save(orderSchedule);

        int databaseSizeBeforeDelete = orderScheduleRepository.findAll().size();

        // Delete the orderSchedule
        restOrderScheduleMockMvc.perform(delete("/api/order-schedules/{id}", orderSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderSchedule> orderScheduleList = orderScheduleRepository.findAll();
        assertThat(orderScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderSchedule.class);
        OrderSchedule orderSchedule1 = new OrderSchedule();
        orderSchedule1.setId(1L);
        OrderSchedule orderSchedule2 = new OrderSchedule();
        orderSchedule2.setId(orderSchedule1.getId());
        assertThat(orderSchedule1).isEqualTo(orderSchedule2);
        orderSchedule2.setId(2L);
        assertThat(orderSchedule1).isNotEqualTo(orderSchedule2);
        orderSchedule1.setId(null);
        assertThat(orderSchedule1).isNotEqualTo(orderSchedule2);
    }
}
