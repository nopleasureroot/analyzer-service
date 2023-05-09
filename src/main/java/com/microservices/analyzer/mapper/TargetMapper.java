package com.microservices.analyzer.mapper;

import java.sql.Timestamp;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.microservices.analyzer.db.TargetEntity;


@Mapper(componentModel = "spring", imports = { Timestamp.class, UUID.class})
public interface TargetMapper {

    @Mapping(target = "updatedAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "state", source = "state")
    TargetEntity updateEntity(@MappingTarget TargetEntity targetEntity, String state);
}
