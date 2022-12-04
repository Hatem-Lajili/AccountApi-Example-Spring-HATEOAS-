package com.lajili.accountapi.service;

import com.lajili.accountapi.entity.Account;
import com.lajili.accountapi.exception.AccountNotFoundException;
import com.lajili.accountapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> AllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Integer id){
        return accountRepository.findById(id).get();
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Account deposit(float amount, Integer id) {
        accountRepository.deposit(amount, id);
        return accountRepository.findById(id).get();
    }

    public Account withDraw(float amount, Integer id){
        accountRepository.updateBalance(-amount,id);
        return accountRepository.findById(id).get();
    }

    public void deleteAccount(Integer id) throws AccountNotFoundException {
        if(!accountRepository.existsById(id)) throw new AccountNotFoundException();
        accountRepository.deleteById(id);
    }
}
