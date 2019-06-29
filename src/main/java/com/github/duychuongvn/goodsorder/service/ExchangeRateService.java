package com.github.duychuongvn.goodsorder.service;

import com.github.duychuongvn.goodsorder.domain.ExchangeRate;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ExchangeRate}.
 */
public interface ExchangeRateService {

    /**
     * Save a exchangeRate.
     *
     * @param exchangeRate the entity to save.
     * @return the persisted entity.
     */
    ExchangeRate save(ExchangeRate exchangeRate);

    /**
     * Get all the exchangeRates.
     *
     * @return the list of entities.
     */
    List<ExchangeRate> findAll();


    /**
     * Get the "id" exchangeRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExchangeRate> findOne(String id);

    /**
     * Delete the "id" exchangeRate.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
