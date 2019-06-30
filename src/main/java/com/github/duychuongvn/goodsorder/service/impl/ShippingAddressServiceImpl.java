package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.goodsorder.service.ShippingAddressService;
import com.github.duychuongvn.goodsorder.domain.ShippingAddress;
import com.github.duychuongvn.goodsorder.repository.ShippingAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ShippingAddress}.
 */
@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);

    private final ShippingAddressRepository shippingAddressRepository;

    public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
    }

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddress the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        log.debug("Request to save ShippingAddress : {}", shippingAddress);
        removeDefaultAddress(shippingAddress);
        return shippingAddressRepository.save(shippingAddress);
    }

    private void removeDefaultAddress(ShippingAddress shippingAddress) {
        if (shippingAddress.isDefaultAddress()) {
            List<ShippingAddress> shippingAddresses = shippingAddressRepository.findByUserIsCurrentUser();
            if (!shippingAddresses.isEmpty()) {
                shippingAddresses.forEach(address -> address.setDefaultAddress(false));
                shippingAddressRepository.saveAll(shippingAddresses);
            }
        }
    }

    /**
     * Get all the shippingAddresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShippingAddress> findAll() {
        log.debug("Request to get all ShippingAddresses");
        return shippingAddressRepository.findAll();
    }


    /**
     * Get one shippingAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShippingAddress> findOne(Long id) {
        log.debug("Request to get ShippingAddress : {}", id);
        return shippingAddressRepository.findById(id);
    }

    /**
     * Delete the shippingAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingAddress : {}", id);
        shippingAddressRepository.deleteById(id);
    }
}
