package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.GoodsorderApp;
import com.github.duychuongvn.goodsorder.domain.BankAccount;
import com.github.duychuongvn.goodsorder.repository.BankAccountRepository;
import com.github.duychuongvn.goodsorder.service.BankAccountService;
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
 * Integration tests for the {@Link BankAccountResource} REST controller.
 */
@SpringBootTest(classes = GoodsorderApp.class)
public class BankAccountResourceIT {

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_INFO = "AAAAAAAAAA";
    private static final String UPDATED_BANK_INFO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountService bankAccountService;

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

    private MockMvc restBankAccountMockMvc;

    private BankAccount bankAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankAccountResource bankAccountResource = new BankAccountResource(bankAccountService);
        this.restBankAccountMockMvc = MockMvcBuilders.standaloneSetup(bankAccountResource)
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
    public static BankAccount createEntity(EntityManager em) {
        BankAccount bankAccount = new BankAccount()
            .account(DEFAULT_ACCOUNT)
            .fullName(DEFAULT_FULL_NAME)
            .bankInfo(DEFAULT_BANK_INFO)
            .createdAt(DEFAULT_CREATED_AT)
            .lastUpdatedAt(DEFAULT_LAST_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return bankAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccount createUpdatedEntity(EntityManager em) {
        BankAccount bankAccount = new BankAccount()
            .account(UPDATED_ACCOUNT)
            .fullName(UPDATED_FULL_NAME)
            .bankInfo(UPDATED_BANK_INFO)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return bankAccount;
    }

    @BeforeEach
    public void initTest() {
        bankAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankAccount() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // Create the BankAccount
        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isCreated());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testBankAccount.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testBankAccount.getBankInfo()).isEqualTo(DEFAULT_BANK_INFO);
        assertThat(testBankAccount.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBankAccount.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testBankAccount.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBankAccount.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createBankAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // Create the BankAccount with an existing ID
        bankAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get all the bankAccountList
        restBankAccountMockMvc.perform(get("/api/bank-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankInfo").value(hasItem(DEFAULT_BANK_INFO.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", bankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccount.getId().intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.bankInfo").value(DEFAULT_BANK_INFO.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankAccount() throws Exception {
        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankAccount() throws Exception {
        // Initialize the database
        bankAccountService.save(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount
        BankAccount updatedBankAccount = bankAccountRepository.findById(bankAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccount are not directly saved in db
        em.detach(updatedBankAccount);
        updatedBankAccount
            .account(UPDATED_ACCOUNT)
            .fullName(UPDATED_FULL_NAME)
            .bankInfo(UPDATED_BANK_INFO)
            .createdAt(UPDATED_CREATED_AT)
            .lastUpdatedAt(UPDATED_LAST_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restBankAccountMockMvc.perform(put("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankAccount)))
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testBankAccount.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testBankAccount.getBankInfo()).isEqualTo(UPDATED_BANK_INFO);
        assertThat(testBankAccount.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBankAccount.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testBankAccount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBankAccount.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Create the BankAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountMockMvc.perform(put("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankAccount() throws Exception {
        // Initialize the database
        bankAccountService.save(bankAccount);

        int databaseSizeBeforeDelete = bankAccountRepository.findAll().size();

        // Delete the bankAccount
        restBankAccountMockMvc.perform(delete("/api/bank-accounts/{id}", bankAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1L);
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(bankAccount1.getId());
        assertThat(bankAccount1).isEqualTo(bankAccount2);
        bankAccount2.setId(2L);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
        bankAccount1.setId(null);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
    }
}
