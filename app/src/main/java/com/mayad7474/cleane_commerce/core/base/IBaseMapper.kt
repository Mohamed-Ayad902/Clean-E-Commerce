package com.mayad7474.cleane_commerce.core.base

interface IBaseMapper<Dto, Domain> {
    fun mapToDomain(dto: Dto): Domain
    fun mapToDto(domain: Domain): Dto
}