package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.core.util.DateTimeUtil;
import com.github.duychuongvn.goodsorder.domain.ExchangeRate;
import com.github.duychuongvn.goodsorder.repository.ExchangeRateRepository;
import com.github.duychuongvn.goodsorder.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ExchangeRate}.
 */
@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Save a exchangeRate.
     *
     * @param exchangeRate the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        log.debug("Request to save ExchangeRate : {}", exchangeRate);
        return exchangeRateRepository.save(exchangeRate);
    }

    /**
     * Get all the exchangeRates.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExchangeRate> findAll() {
        log.debug("Request to get all ExchangeRates");
        return exchangeRateRepository.findAll();
    }


    /**
     * Get one exchangeRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExchangeRate> findOne(String id) {
        log.debug("Request to get ExchangeRate : {}", id);
        return exchangeRateRepository.findById(id);
    }

    /**
     * Delete the exchangeRate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ExchangeRate : {}", id);
        exchangeRateRepository.deleteById(id);
    }

    @Override
    public ExchangeRate getCurrentExchangeRate() {
        String currentDate = DateTimeUtil.formatDate(LocalDate.now(), DateTimeUtil.YEAR_MONTH_DATE);
        Optional<ExchangeRate> rate = exchangeRateRepository.findById(currentDate);
        return rate.orElseGet(exchangeRateRepository::findFirstByOrderByIdDesc);
    }
}
