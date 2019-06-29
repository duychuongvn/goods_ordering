package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.GoodsorderApp;
import com.github.duychuongvn.goodsorder.domain.Order;
import com.github.duychuongvn.goodsorder.repository.OrderRepository;
import com.github.duychuongvn.goodsorder.service.OrderService;
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
import java.math.BigDecimal;
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

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderStatus;
import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;
/**
 * Integration tests for the {@Link OrderResource} REST controller.
 */
@SpringBootTest(classes = GoodsorderApp.class)
public class OrderResourceIT {

    private static final String DEFAULT_PAYMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.PENDING;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.CONFIRMED;

    private static final DeliveryStatus DEFAULT_DELIVERY_STATUS = DeliveryStatus.INIT;
    private static final DeliveryStatus UPDATED_DELIVERY_STATUS = DeliveryStatus.PICKED_UP;

    private static final String DEFAULT_EXCHANGE_RATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_RATE_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_EXCHANGE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXCHANGE_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_JPY_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_JPY_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DELIVERY_FEE_VND = new BigDecimal(1);
    private static final BigDecimal UPDATED_DELIVERY_FEE_VND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PAY_VND = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PAY_VND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DEPOSITED_VND = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEPOSITED_VND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAID_VND = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAID_VND = new BigDecimal(2);

    private static final LocalDate DEFAULT_PACKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PACKING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ESTIMATED_DELIVER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_DELIVER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELIVERED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FINISH_PAYMENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINISH_PAYMENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

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

    private MockMvc restOrderMockMvc;

    private Order order;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderResource orderResource = new OrderResource(orderService);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource)
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
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .paymentCode(DEFAULT_PAYMENT_CODE)
            .orderDate(DEFAULT_ORDER_DATE)
            .status(DEFAULT_STATUS)
            .deliveryStatus(DEFAULT_DELIVERY_STATUS)
            .exchangeRateId(DEFAULT_EXCHANGE_RATE_ID)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .totalJpyPrice(DEFAULT_TOTAL_JPY_PRICE)
            .deliveryFeeVnd(DEFAULT_DELIVERY_FEE_VND)
            .totalPayVnd(DEFAULT_TOTAL_PAY_VND)
            .depositedVnd(DEFAULT_DEPOSITED_VND)
            .paidVnd(DEFAULT_PAID_VND)
            .packingDate(DEFAULT_PACKING_DATE)
            .estimatedDeliverDate(DEFAULT_ESTIMATED_DELIVER_DATE)
            .deliveredDate(DEFAULT_DELIVERED_DATE)
            .finishPaymentTime(DEFAULT_FINISH_PAYMENT_TIME)
            .remark(DEFAULT_REMARK)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .email(DEFAULT_EMAIL)
            .zipCode(DEFAULT_ZIP_CODE)
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .createdAt(DEFAULT_CREATED_AT)
            .lastUpdatedAt(DEFAULT_LAST_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .paymentCode(UPDATED_PAYMENT_CODE)
            .orderDate(UPDATED_ORDER_DATE)
            .status(UPDATED_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .exchangeRateId(UPDATED_EXCHANGE_RATE_ID)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .totalJpyPrice(UPDATED_TOTAL_JPY_PRICE)
            .deliveryFeeVnd(UPDATED_DELIVERY_FEE_VND)
            .totalPayVnd(UPDATED_TOTAL_PAY_VND)
            .depositedVnd(UPDATED_DEPOSITED_VND)
            .paidVnd(UPDATED_PAID_VND)
            .packingDate(UPDATED_PACKING_DATE)
            .estimatedDeliverDate(UPDATED_ESTIMATED_DELIVER_DATE)
            .deliveredDate(UPDATED_DELIVERED_DATE)
            .finishPaymentTime(UPDATED_FINISH_PAYMENT_TIME)
            .remark(UPDATED_REMARK)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email(UPDATED_EMAIL)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getPaymentCode()).isEqualTo(DEFAULT_PAYMENT_CODE);
        assertThat(testOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrder.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
        assertThat(testOrder.getExchangeRateId()).isEqualTo(DEFAULT_EXCHANGE_RATE_ID);
        assertThat(testOrder.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testOrder.getTotalJpyPrice()).isEqualTo(DEFAULT_TOTAL_JPY_PRICE);
        assertThat(testOrder.getDeliveryFeeVnd()).isEqualTo(DEFAULT_DELIVERY_FEE_VND);
        assertThat(testOrder.getTotalPayVnd()).isEqualTo(DEFAULT_TOTAL_PAY_VND);
        assertThat(testOrder.getDepositedVnd()).isEqualTo(DEFAULT_DEPOSITED_VND);
        assertThat(testOrder.getPaidVnd()).isEqualTo(DEFAULT_PAID_VND);
        assertThat(testOrder.getPackingDate()).isEqualTo(DEFAULT_PACKING_DATE);
        assertThat(testOrder.getEstimatedDeliverDate()).isEqualTo(DEFAULT_ESTIMATED_DELIVER_DATE);
        assertThat(testOrder.getDeliveredDate()).isEqualTo(DEFAULT_DELIVERED_DATE);
        assertThat(testOrder.getFinishPaymentTime()).isEqualTo(DEFAULT_FINISH_PAYMENT_TIME);
        assertThat(testOrder.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testOrder.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testOrder.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testOrder.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testOrder.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testOrder.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrder.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testOrder.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOrder.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testOrder.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrder.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testOrder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrder.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPaymentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setPaymentCode(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setStatus(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExchangeRateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setExchangeRateId(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentCode").value(hasItem(DEFAULT_PAYMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].exchangeRateId").value(hasItem(DEFAULT_EXCHANGE_RATE_ID.toString())))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.intValue())))
            .andExpect(jsonPath("$.[*].totalJpyPrice").value(hasItem(DEFAULT_TOTAL_JPY_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].deliveryFeeVnd").value(hasItem(DEFAULT_DELIVERY_FEE_VND.intValue())))
            .andExpect(jsonPath("$.[*].totalPayVnd").value(hasItem(DEFAULT_TOTAL_PAY_VND.intValue())))
            .andExpect(jsonPath("$.[*].depositedVnd").value(hasItem(DEFAULT_DEPOSITED_VND.intValue())))
            .andExpect(jsonPath("$.[*].paidVnd").value(hasItem(DEFAULT_PAID_VND.intValue())))
            .andExpect(jsonPath("$.[*].packingDate").value(hasItem(DEFAULT_PACKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedDeliverDate").value(hasItem(DEFAULT_ESTIMATED_DELIVER_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveredDate").value(hasItem(DEFAULT_DELIVERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishPaymentTime").value(hasItem(sameInstant(DEFAULT_FINISH_PAYMENT_TIME))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.paymentCode").value(DEFAULT_PAYMENT_CODE.toString()))
            .andExpect(jsonPath("$.orderDate").value(sameInstant(DEFAULT_ORDER_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.deliveryStatus").value(DEFAULT_DELIVERY_STATUS.toString()))
            .andExpect(jsonPath("$.exchangeRateId").value(DEFAULT_EXCHANGE_RATE_ID.toString()))
            .andExpect(jsonPath("$.exchangeRate").value(DEFAULT_EXCHANGE_RATE.intValue()))
            .andExpect(jsonPath("$.totalJpyPrice").value(DEFAULT_TOTAL_JPY_PRICE.intValue()))
            .andExpect(jsonPath("$.deliveryFeeVnd").value(DEFAULT_DELIVERY_FEE_VND.intValue()))
            .andExpect(jsonPath("$.totalPayVnd").value(DEFAULT_TOTAL_PAY_VND.intValue()))
            .andExpect(jsonPath("$.depositedVnd").value(DEFAULT_DEPOSITED_VND.intValue()))
            .andExpect(jsonPath("$.paidVnd").value(DEFAULT_PAID_VND.intValue()))
            .andExpect(jsonPath("$.packingDate").value(DEFAULT_PACKING_DATE.toString()))
            .andExpect(jsonPath("$.estimatedDeliverDate").value(DEFAULT_ESTIMATED_DELIVER_DATE.toString()))
            .andExpect(jsonPath("$.deliveredDate").value(DEFAULT_DELIVERED_DATE.toString()))
            .andExpect(jsonPath("$.finishPaymentTime").value(sameInstant(DEFAULT_FINISH_PAYMENT_TIME)))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .paymentCode(UPDATED_PAYMENT_CODE)
            .orderDate(UPDATED_ORDER_DATE)
            .status(UPDATED_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .exchangeRateId(UPDATED_EXCHANGE_RATE_ID)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .totalJpyPrice(UPDATED_TOTAL_JPY_PRICE)
            .deliveryFeeVnd(UPDATED_DELIVERY_FEE_VND)
            .totalPayVnd(UPDATED_TOTAL_PAY_VND)
            .depositedVnd(UPDATED_DEPOSITED_VND)
            .paidVnd(UPDATED_PAID_VND)
            .packingDate(UPDATED_PACKING_DATE)
            .estimatedDeliverDate(UPDATED_ESTIMATED_DELIVER_DATE)
            .deliveredDate(UPDATED_DELIVERED_DATE)
            .finishPaymentTime(UPDATED_FINISH_PAYMENT_TIME)
            .remark(UPDATED_REMARK)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email(UPDATED_EMAIL)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrder)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrder.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
        assertThat(testOrder.getExchangeRateId()).isEqualTo(UPDATED_EXCHANGE_RATE_ID);
        assertThat(testOrder.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testOrder.getTotalJpyPrice()).isEqualTo(UPDATED_TOTAL_JPY_PRICE);
        assertThat(testOrder.getDeliveryFeeVnd()).isEqualTo(UPDATED_DELIVERY_FEE_VND);
        assertThat(testOrder.getTotalPayVnd()).isEqualTo(UPDATED_TOTAL_PAY_VND);
        assertThat(testOrder.getDepositedVnd()).isEqualTo(UPDATED_DEPOSITED_VND);
        assertThat(testOrder.getPaidVnd()).isEqualTo(UPDATED_PAID_VND);
        assertThat(testOrder.getPackingDate()).isEqualTo(UPDATED_PACKING_DATE);
        assertThat(testOrder.getEstimatedDeliverDate()).isEqualTo(UPDATED_ESTIMATED_DELIVER_DATE);
        assertThat(testOrder.getDeliveredDate()).isEqualTo(UPDATED_DELIVERED_DATE);
        assertThat(testOrder.getFinishPaymentTime()).isEqualTo(UPDATED_FINISH_PAYMENT_TIME);
        assertThat(testOrder.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testOrder.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testOrder.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testOrder.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testOrder.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testOrder.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrder.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testOrder.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOrder.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrder.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrder.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);
        order2.setId(2L);
        assertThat(order1).isNotEqualTo(order2);
        order1.setId(null);
        assertThat(order1).isNotEqualTo(order2);
    }
}
