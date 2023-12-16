package com.ktxdevelopment.domain.repo


import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel

interface LocalRepository {
    fun savePeople(people: List<ResidentModel>)

    fun saveCities(cities: List<CityModel>)

    fun saveCountries(countries: List<CountryModel>)

    fun getCountries(): List<CountryModel>

    fun getCities(): List<CityModel>

    fun getResidents(cities: List<Long>): List<ResidentModel>

    fun getAllResidents(): List<ResidentModel>
}