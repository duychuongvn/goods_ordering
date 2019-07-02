package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.OrderSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


/**
 * Spring Data  repository for the OrderSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderScheduleRepository extends JpaRepository<OrderSchedule, Long> {

    @Query("select os from OrderSchedule os where os.status = 'OPEN' and os.openDate <= current_date and os.closeDate >= current_date ")
    OrderSchedule findOpenOrderSchedule();
}
