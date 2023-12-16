package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidenceEntity

interface LocalRepository {
    fun savePeople(people: List<ResidenceEntity>)

    fun saveCities(cities: List<CityEntity>)

    fun saveCountries(countries: List<CountryEntity>)
}