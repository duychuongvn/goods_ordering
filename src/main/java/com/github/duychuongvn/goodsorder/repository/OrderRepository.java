package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.Order;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select order from Order order where order.user.login = ?#{principal.username}")
    List<Order> findByUserIsCurrentUser();

    @Query("select order from Order order where order.merchant.login = ?#{principal.username}")
    List<Order> findByMerchantIsCurrentUser();

}
