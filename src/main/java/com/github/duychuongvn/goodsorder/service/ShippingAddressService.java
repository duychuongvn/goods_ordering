package com.github.duychuongvn.goodsorder.service;

import com.github.duychuongvn.goodsorder.domain.ShippingAddress;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ShippingAddress}.
 */
public interface ShippingAddressService {

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddress the entity to save.
     * @return the persisted entity.
     */
    ShippingAddress save(ShippingAddress shippingAddress);

    /**
     * Get all the shippingAddresses.
     *
     * @return the list of entities.
     */
    List<ShippingAddress> findAll();


    /**
     * Get the "id" shippingAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShippingAddress> findOne(Long id);

    /**
     * Delete the "id" shippingAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
