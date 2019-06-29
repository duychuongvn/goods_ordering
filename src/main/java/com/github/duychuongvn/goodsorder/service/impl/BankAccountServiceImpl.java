package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.goodsorder.service.BankAccountService;
import com.github.duychuongvn.goodsorder.domain.BankAccount;
import com.github.duychuongvn.goodsorder.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BankAccount}.
 */
@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Save a bankAccount.
     *
     * @param bankAccount the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BankAccount save(BankAccount bankAccount) {
        log.debug("Request to save BankAccount : {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Get all the bankAccounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankAccount> findAll() {
        log.debug("Request to get all BankAccounts");
        return bankAccountRepository.findAll();
    }


    /**
     * Get one bankAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BankAccount> findOne(Long id) {
        log.debug("Request to get BankAccount : {}", id);
        return bankAccountRepository.findById(id);
    }

    /**
     * Delete the bankAccount by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankAccount : {}", id);
        bankAccountRepository.deleteById(id);
    }
}
