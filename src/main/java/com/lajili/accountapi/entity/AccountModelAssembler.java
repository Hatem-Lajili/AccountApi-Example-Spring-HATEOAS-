package com.lajili.accountapi.entity;

import com.lajili.accountapi.controller.AccountController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Configuration
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {
    @Override
    public EntityModel<Account> toModel(Account entity) {
        EntityModel<Account> accountModel = EntityModel.of(entity);

        accountModel.add(linkTo(methodOn(AccountController.class).getAccountById(entity.getId())).withSelfRel());
        accountModel.add(linkTo(methodOn(AccountController.class).listAllAccount()).withRel(IanaLinkRelations.COLLECTION));
        accountModel.add(linkTo(methodOn(AccountController.class).deposit(entity.getId(), null)).withRel("deposits"));
        accountModel.add(linkTo(methodOn(AccountController.class).withdraw(entity.getId(), null)).withRel("withdrawal"));

        return accountModel;

    }
}
