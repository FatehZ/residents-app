package com.ktxdevelopment.data.local.repo

import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidentEntity
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.LocalRepository
import com.ktxdevelopment.domain.util.toDomain
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var countryDao: CountryDao,
    private var personDao: PersonDao,
    private var cityDao: CityDao
) : LocalRepository {
    override fun savePeople(people: List<ResidentEntity>) {
        personDao.insertPerson(people)
    }

    override fun saveCities(cities: List<CityEntity>) {
        cityDao.insertCities(cities)
    }

    override fun saveCountries(countries: List<CountryEntity>) {
        countryDao.insertCountries(countries)
    }

    override fun getCountries(): List<CountryModel> = countryDao.getAllCountries().toDomain()


    override fun getCities(): List<CityModel> = cityDao.getAllCities().toDomain()

    override fun getResidents(cities: List<String>): List<ResidentModel> =
        personDao.getPeopleByCityIds(cities).toDomain()
}