package com.lajili.accountapi.controller;

import com.lajili.accountapi.entity.Account;
import com.lajili.accountapi.entity.AccountModelAssembler;
import com.lajili.accountapi.entity.Amount;
import com.lajili.accountapi.exception.AccountNotFoundException;
import com.lajili.accountapi.service.AccountService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private AccountModelAssembler assembler;

    public AccountController(AccountService accountService , AccountModelAssembler assembler) {
        this.accountService = accountService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity <CollectionModel<EntityModel<Account>>> listAllAccount(){
        List<Account> accountList = accountService.AllAccounts();

        if (accountList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Account>> accounts = accountList.stream().map(assembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Account>> model = CollectionModel.of(accounts);
        model.add(linkTo(methodOn(AccountController.class).listAllAccount()).withSelfRel());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Account>> getAccountById(@PathVariable("id") Integer id){

        try{
            var account = accountService.getAccountById(id);

            EntityModel<Account> entityModel = assembler.toModel(account);

            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<EntityModel<Account>> addAccount(@RequestBody Account account){
        var savedAccount = accountService.saveAccount(account);

        EntityModel<Account> entityModel = assembler.toModel(account);

        return ResponseEntity.created(
                linkTo(methodOn(AccountController.class).getAccountById(savedAccount.getId())).toUri())
                .body(entityModel);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Account>> updateAccount(@RequestBody Account account){
        Account updatedAccount = accountService.saveAccount(account);

        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);


        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposits")
    public HttpEntity<EntityModel<Account>> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount) {

        var updatedAccount = accountService.deposit(amount.getAmount(), id);

        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PatchMapping("/{id}/withdrawal")
    public ResponseEntity<EntityModel<Account>> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount) {
        var updatedAccount = accountService.withDraw(amount.getAmount(), id);

        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        }
        catch (AccountNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

}
