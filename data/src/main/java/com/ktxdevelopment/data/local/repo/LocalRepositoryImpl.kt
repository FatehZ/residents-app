package com.ktxdevelopment.data.local.repo

import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.util.toCityDomain
import com.ktxdevelopment.data.util.toCityEntity
import com.ktxdevelopment.data.util.toCountryDomain
import com.ktxdevelopment.data.util.toCountryEntity
import com.ktxdevelopment.data.util.toResidentDomain
import com.ktxdevelopment.data.util.toResidentEntity
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var countryDao: CountryDao,
    private var personDao: PersonDao,
    private var cityDao: CityDao
) : LocalRepository {

    override fun savePeople(people: List<ResidentModel>) {
        personDao.insertPerson(people.toResidentEntity())
    }

    override fun saveCities(cities: List<CityModel>) {
        cityDao.insertCities(cities.toCityEntity())
    }

    override fun saveCountries(countries: List<CountryModel>) {
        countryDao.insertCountries(countries.toCountryEntity())
    }

    override fun getCountries(): List<CountryModel> = countryDao.getAllCountries().toCountryDomain()


    override fun getCities(): List<CityModel> = cityDao.getAllCities().toCityDomain()

    override fun getAllResidents() = personDao.getAllPeople().toResidentDomain()

    override fun getResidents(cities: List<Long>): List<ResidentModel> = personDao.getPeopleByCityIds(cities).toResidentDomain()
}