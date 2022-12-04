package com.lajili.accountapi;

import com.lajili.accountapi.entity.Account;
import com.lajili.accountapi.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataBaseLoader {

    private AccountRepository accountRepository;

    public DataBaseLoader(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Bean
    public CommandLineRunner initDataBase(){
        return args -> {
            Account account1 = new Account("123456789",100);
            Account account2 = new Account("987654321",50);
            Account account3 = new Account("002356897",150);
            accountRepository.saveAll(List.of(account1,account2,account3));
            System.out.println("DataBase Initialized");
        };
    }
}
