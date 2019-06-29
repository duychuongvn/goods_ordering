package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.GoodsorderApp;
import com.github.duychuongvn.goodsorder.domain.OrderLineItem;
import com.github.duychuongvn.goodsorder.repository.OrderLineItemRepository;
import com.github.duychuongvn.goodsorder.service.OrderLineItemService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.github.duychuongvn.goodsorder.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
/**
 * Integration tests for the {@Link OrderLineItemResource} REST controller.
 */
@SpringBootTest(classes = GoodsorderApp.class)
public class OrderLineItemResourceIT {

    private static final String DEFAULT_REFERENCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_URL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ORIGIN_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORIGIN_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SALE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALE_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final String DEFAULT_GOODS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOODS_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GOODS_SKU = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGES_CONTENT_TYPE = "image/png";

    private static final OrderSource DEFAULT_SOURCE = OrderSource.AEO_JP;
    private static final OrderSource UPDATED_SOURCE = OrderSource.ABC_MART;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    private OrderLineItemService orderLineItemService;

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

    private MockMvc restOrderLineItemMockMvc;

    private OrderLineItem orderLineItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderLineItemResource orderLineItemResource = new OrderLineItemResource(orderLineItemService);
        this.restOrderLineItemMockMvc = MockMvcBuilders.standaloneSetup(orderLineItemResource)
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
    public static OrderLineItem createEntity(EntityManager em) {
        OrderLineItem orderLineItem = new OrderLineItem()
            .referenceUrl(DEFAULT_REFERENCE_URL)
            .originPrice(DEFAULT_ORIGIN_PRICE)
            .salePrice(DEFAULT_SALE_PRICE)
            .tax(DEFAULT_TAX)
            .goodsName(DEFAULT_GOODS_NAME)
            .goodsId(DEFAULT_GOODS_ID)
            .goodsSKU(DEFAULT_GOODS_SKU)
            .size(DEFAULT_SIZE)
            .remark(DEFAULT_REMARK)
            .images(DEFAULT_IMAGES)
            .imagesContentType(DEFAULT_IMAGES_CONTENT_TYPE)
            .source(DEFAULT_SOURCE);
        return orderLineItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLineItem createUpdatedEntity(EntityManager em) {
        OrderLineItem orderLineItem = new OrderLineItem()
            .referenceUrl(UPDATED_REFERENCE_URL)
            .originPrice(UPDATED_ORIGIN_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .tax(UPDATED_TAX)
            .goodsName(UPDATED_GOODS_NAME)
            .goodsId(UPDATED_GOODS_ID)
            .goodsSKU(UPDATED_GOODS_SKU)
            .size(UPDATED_SIZE)
            .remark(UPDATED_REMARK)
            .images(UPDATED_IMAGES)
            .imagesContentType(UPDATED_IMAGES_CONTENT_TYPE)
            .source(UPDATED_SOURCE);
        return orderLineItem;
    }

    @BeforeEach
    public void initTest() {
        orderLineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderLineItem() throws Exception {
        int databaseSizeBeforeCreate = orderLineItemRepository.findAll().size();

        // Create the OrderLineItem
        restOrderLineItemMockMvc.perform(post("/api/order-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLineItem)))
            .andExpect(status().isCreated());

        // Validate the OrderLineItem in the database
        List<OrderLineItem> orderLineItemList = orderLineItemRepository.findAll();
        assertThat(orderLineItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderLineItem testOrderLineItem = orderLineItemList.get(orderLineItemList.size() - 1);
        assertThat(testOrderLineItem.getReferenceUrl()).isEqualTo(DEFAULT_REFERENCE_URL);
        assertThat(testOrderLineItem.getOriginPrice()).isEqualTo(DEFAULT_ORIGIN_PRICE);
        assertThat(testOrderLineItem.getSalePrice()).isEqualTo(DEFAULT_SALE_PRICE);
        assertThat(testOrderLineItem.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testOrderLineItem.getGoodsName()).isEqualTo(DEFAULT_GOODS_NAME);
        assertThat(testOrderLineItem.getGoodsId()).isEqualTo(DEFAULT_GOODS_ID);
        assertThat(testOrderLineItem.getGoodsSKU()).isEqualTo(DEFAULT_GOODS_SKU);
        assertThat(testOrderLineItem.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testOrderLineItem.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testOrderLineItem.getImages()).isEqualTo(DEFAULT_IMAGES);
        assertThat(testOrderLineItem.getImagesContentType()).isEqualTo(DEFAULT_IMAGES_CONTENT_TYPE);
        assertThat(testOrderLineItem.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    public void createOrderLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderLineItemRepository.findAll().size();

        // Create the OrderLineItem with an existing ID
        orderLineItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderLineItemMockMvc.perform(post("/api/order-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLineItem)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLineItem in the database
        List<OrderLineItem> orderLineItemList = orderLineItemRepository.findAll();
        assertThat(orderLineItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReferenceUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLineItemRepository.findAll().size();
        // set the field null
        orderLineItem.setReferenceUrl(null);

        // Create the OrderLineItem, which fails.

        restOrderLineItemMockMvc.perform(post("/api/order-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLineItem)))
            .andExpect(status().isBadRequest());

        List<OrderLineItem> orderLineItemList = orderLineItemRepository.findAll();
        assertThat(orderLineItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderLineItems() throws Exception {
        // Initialize the database
        orderLineItemRepository.saveAndFlush(orderLineItem);

        // Get all the orderLineItemList
        restOrderLineItemMockMvc.perform(get("/api/order-line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceUrl").value(hasItem(DEFAULT_REFERENCE_URL.toString())))
            .andExpect(jsonPath("$.[*].originPrice").value(hasItem(DEFAULT_ORIGIN_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
            .andExpect(jsonPath("$.[*].goodsName").value(hasItem(DEFAULT_GOODS_NAME.toString())))
            .andExpect(jsonPath("$.[*].goodsId").value(hasItem(DEFAULT_GOODS_ID.toString())))
            .andExpect(jsonPath("$.[*].goodsSKU").value(hasItem(DEFAULT_GOODS_SKU.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].imagesContentType").value(hasItem(DEFAULT_IMAGES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].images").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGES))))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderLineItem() throws Exception {
        // Initialize the database
        orderLineItemRepository.saveAndFlush(orderLineItem);

        // Get the orderLineItem
        restOrderLineItemMockMvc.perform(get("/api/order-line-items/{id}", orderLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderLineItem.getId().intValue()))
            .andExpect(jsonPath("$.referenceUrl").value(DEFAULT_REFERENCE_URL.toString()))
            .andExpect(jsonPath("$.originPrice").value(DEFAULT_ORIGIN_PRICE.intValue()))
            .andExpect(jsonPath("$.salePrice").value(DEFAULT_SALE_PRICE.intValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.intValue()))
            .andExpect(jsonPath("$.goodsName").value(DEFAULT_GOODS_NAME.toString()))
            .andExpect(jsonPath("$.goodsId").value(DEFAULT_GOODS_ID.toString()))
            .andExpect(jsonPath("$.goodsSKU").value(DEFAULT_GOODS_SKU.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.imagesContentType").value(DEFAULT_IMAGES_CONTENT_TYPE))
            .andExpect(jsonPath("$.images").value(Base64Utils.encodeToString(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderLineItem() throws Exception {
        // Get the orderLineItem
        restOrderLineItemMockMvc.perform(get("/api/order-line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderLineItem() throws Exception {
        // Initialize the database
        orderLineItemService.save(orderLineItem);

        int databaseSizeBeforeUpdate = orderLineItemRepository.findAll().size();

        // Update the orderLineItem
        OrderLineItem updatedOrderLineItem = orderLineItemRepository.findById(orderLineItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrderLineItem are not directly saved in db
        em.detach(updatedOrderLineItem);
        updatedOrderLineItem
            .referenceUrl(UPDATED_REFERENCE_URL)
            .originPrice(UPDATED_ORIGIN_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .tax(UPDATED_TAX)
            .goodsName(UPDATED_GOODS_NAME)
            .goodsId(UPDATED_GOODS_ID)
            .goodsSKU(UPDATED_GOODS_SKU)
            .size(UPDATED_SIZE)
            .remark(UPDATED_REMARK)
            .images(UPDATED_IMAGES)
            .imagesContentType(UPDATED_IMAGES_CONTENT_TYPE)
            .source(UPDATED_SOURCE);

        restOrderLineItemMockMvc.perform(put("/api/order-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderLineItem)))
            .andExpect(status().isOk());

        // Validate the OrderLineItem in the database
        List<OrderLineItem> orderLineItemList = orderLineItemRepository.findAll();
        assertThat(orderLineItemList).hasSize(databaseSizeBeforeUpdate);
        OrderLineItem testOrderLineItem = orderLineItemList.get(orderLineItemList.size() - 1);
        assertThat(testOrderLineItem.getReferenceUrl()).isEqualTo(UPDATED_REFERENCE_URL);
        assertThat(testOrderLineItem.getOriginPrice()).isEqualTo(UPDATED_ORIGIN_PRICE);
        assertThat(testOrderLineItem.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testOrderLineItem.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testOrderLineItem.getGoodsName()).isEqualTo(UPDATED_GOODS_NAME);
        assertThat(testOrderLineItem.getGoodsId()).isEqualTo(UPDATED_GOODS_ID);
        assertThat(testOrderLineItem.getGoodsSKU()).isEqualTo(UPDATED_GOODS_SKU);
        assertThat(testOrderLineItem.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testOrderLineItem.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testOrderLineItem.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testOrderLineItem.getImagesContentType()).isEqualTo(UPDATED_IMAGES_CONTENT_TYPE);
        assertThat(testOrderLineItem.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderLineItem() throws Exception {
        int databaseSizeBeforeUpdate = orderLineItemRepository.findAll().size();

        // Create the OrderLineItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLineItemMockMvc.perform(put("/api/order-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLineItem)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLineItem in the database
        List<OrderLineItem> orderLineItemList = orderLineItemRepository.findAll();
        assertThat(orderLineItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderLineItem() throws Exception {
        // Initialize the database
        orderLineItemService.save(orderLineItem);

        int databaseSizeBeforeDelete = orderLineItemRepository.findAll().size();

        // Delete the orderLineItem
        restOrderLineItemMockMvc.perform(delete("/api/order-line-items/{id}", orderLineItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderLineItem> orderLineItemList = orderLineItemRepository.findAll();
        assertThat(orderLineItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLineItem.class);
        OrderLineItem orderLineItem1 = new OrderLineItem();
        orderLineItem1.setId(1L);
        OrderLineItem orderLineItem2 = new OrderLineItem();
        orderLineItem2.setId(orderLineItem1.getId());
        assertThat(orderLineItem1).isEqualTo(orderLineItem2);
        orderLineItem2.setId(2L);
        assertThat(orderLineItem1).isNotEqualTo(orderLineItem2);
        orderLineItem1.setId(null);
        assertThat(orderLineItem1).isNotEqualTo(orderLineItem2);
    }
}
