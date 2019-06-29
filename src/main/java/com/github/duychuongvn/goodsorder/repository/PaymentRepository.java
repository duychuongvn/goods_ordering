package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.Payment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
