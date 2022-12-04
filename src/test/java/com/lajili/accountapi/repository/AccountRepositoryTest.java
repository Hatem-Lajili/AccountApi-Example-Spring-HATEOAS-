package com.lajili.accountapi.repository;

import com.lajili.accountapi.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testAddAccount(){
        Account account = new Account("123456789", 100);
        var save = accountRepository.save(account);

        assertThat(save).isNotNull();
        assertThat(save.getId()).isGreaterThan(0);

    }
    @Test
    public void testDepositAmount(){
        Account account = new Account("123456789", 100);
        var save = accountRepository.save(account);

        accountRepository.updateBalance(150, save.getId());

        var updateAccount =  accountRepository.findById(save.getId()).get();

        assertThat(updateAccount.getBalance()).isEqualTo(150);
    }


}