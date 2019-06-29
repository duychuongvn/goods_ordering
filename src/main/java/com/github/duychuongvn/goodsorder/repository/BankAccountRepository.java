package com.github.duychuongvn.goodsorder.repository;

import com.github.duychuongvn.goodsorder.domain.BankAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
