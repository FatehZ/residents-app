package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidentEntity
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel

interface LocalRepository {
    fun savePeople(people: List<ResidentEntity>)

    fun saveCities(cities: List<CityEntity>)

    fun saveCountries(countries: List<CountryEntity>)

    fun getCountries(): List<CountryModel>

    fun getCities(): List<CityModel>

    fun getResidents(cities: List<String>): List<ResidentModel>
}