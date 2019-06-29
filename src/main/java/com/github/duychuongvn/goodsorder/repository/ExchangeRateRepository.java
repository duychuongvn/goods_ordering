package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.ExchangeRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExchangeRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

}
