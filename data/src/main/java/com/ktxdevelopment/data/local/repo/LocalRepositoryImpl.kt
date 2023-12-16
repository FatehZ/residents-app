package com.ktxdevelopment.data.local.repo

import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidenceEntity
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var countryDao: CountryDao,
    private var personDao: PersonDao,
    private var cityDao: CityDao
) : LocalRepository {
    override fun savePeople(people: List<ResidenceEntity>) {
        personDao.insertPerson(people)
    }

    override fun saveCities(cities: List<CityEntity>) {
        cityDao.insertCities(cities)
    }

    override fun saveCountries(countries: List<CountryEntity>) {
        countryDao.insertCountries(countries)
    }
}