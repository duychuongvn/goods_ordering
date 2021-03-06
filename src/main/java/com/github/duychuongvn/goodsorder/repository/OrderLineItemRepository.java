package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.OrderLineItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderLineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {

}
