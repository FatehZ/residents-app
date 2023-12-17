package com.ktxdevelopment.domain.model

data class ResponseDataResult (
    val countries: List<CountryModel>,
    val cities: List<CityModel>,
    val people: List<ResidentModel>
)