package com.ktxdevelopment.domain.repo


import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun savePeople(people: List<ResidentModel>)

    suspend fun saveCities(cities: List<CityModel>)

    suspend fun saveCountries(countries: List<CountryModel>)

    suspend fun getCountries(): Flow<List<CountryModel>>

    suspend fun getCities(): Flow<List<CityModel>>

    suspend fun getResidents(cities: List<Long>): Flow<List<ResidentModel>>

    suspend fun getAllResidents(): Flow<List<ResidentModel>>
}