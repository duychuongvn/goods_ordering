package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.OrderTracking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long> {

}
