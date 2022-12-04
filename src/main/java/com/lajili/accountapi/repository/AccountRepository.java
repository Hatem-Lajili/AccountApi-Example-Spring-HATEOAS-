package com.lajili.accountapi.repository;

import com.lajili.accountapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("UPDATE Account a SET a.balance = a.balance + ?1 WHERE a.id = ?2")
    @Modifying
    void updateBalance (float amount, Integer id);

    @Query("UPDATE Account a SET a.balance = a.balance + ?1 WHERE a.id = ?2")
    @Modifying
    @Transactional
    void deposit(float amount, Integer id);

    @Query("UPDATE Account a SET a.balance = a.balance - ?1 WHERE a.id = ?2")
    @Modifying
    @Transactional
    void withDraw(float amount, Integer id);
}
