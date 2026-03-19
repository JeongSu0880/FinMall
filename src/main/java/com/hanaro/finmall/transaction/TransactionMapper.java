package com.hanaro.finmall.transaction;

import com.hanaro.finmall.transaction.dto.TransactionCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    Transaction toEntity(TransactionCreateDTO dto);

}