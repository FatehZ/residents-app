package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.model.ResponseDataResult
import com.ktxdevelopment.domain.util.TestData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalRepository : LocalRepository {
    private var countries = mutableSetOf<CountryModel>()
    private var residents = mutableSetOf<ResidentModel>()
    private var cities = mutableSetOf<CityModel>()

    override suspend fun savePeople(people: List<ResidentModel>) {
        residents.addAll(people)
    }

    override suspend fun saveCities(cities: List<CityModel>) {
        this.cities.addAll(cities)
    }

    override suspend fun saveCountries(countries: List<CountryModel>) {
        this.countries.addAll(countries)
    }

    override suspend fun getCountries(): Flow<List<CountryModel>> {
        return flow{
            delay(200)
            emit(countries.toList())
        }
    }

    override suspend fun getCities(): Flow<List<CityModel>> {
        return flow{
            delay(1000)
            emit(cities.toList())
        }
    }

    override suspend fun getResidents(cities: List<Long>): Flow<List<ResidentModel>> {
        return flow{
            delay(200)
            emit(residents.filter { it.cityId in cities })
        }
    }

    override suspend fun getAllResidents(): Flow<List<ResidentModel>> {
        return flow{
            delay(200)
            emit(residents.toList())
        }
    }

    override suspend fun clearPeople() {
        residents.clear()
    }

    override suspend fun clearCountries() {
        countries.clear()
    }

    override suspend fun clearCities() {
        cities.clear()
    }
}