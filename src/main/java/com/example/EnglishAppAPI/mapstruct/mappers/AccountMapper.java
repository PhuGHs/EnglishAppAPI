package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Account;
import com.example.EnglishAppAPI.mapstruct.dtos.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AccountMapper {
    @Mapping(target = "user", source = "user")
    AccountDto toDto(Account account);
}
