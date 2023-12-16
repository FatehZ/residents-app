package com.ktxdevelopment.domain.util
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidenceEntity
import com.ktxdevelopment.data.network.model.HttpResponseModel


//  For better performance this way is preferred, although a bit confusing.
//  Returning 3 list in a hashmap with keys would be better, but this way is simpler.
fun HttpResponseModel.toEntitiesOfPersonCityCountry() : Array<List<Any>> {
    val personList = arrayListOf<ResidenceEntity>()
    val cityList = arrayListOf<CityEntity>()
    val countryList = arrayListOf<CountryEntity>()

    this.countryList.forEach { country ->
        countryList.add(CountryEntity(name = country.name, countryId = country.countryId.toLong()))

        country.cityList.forEach { city ->
            cityList.add(CityEntity(city.cityId.toLong(), country.countryId.toLong(), city.name))

            city.peopleList.forEach { person ->
                personList.add(
                    ResidenceEntity(
                        humanId = person.humanId.toLong(),
                        cityId = city.cityId.toLong(),
                        name = person.name,
                        surname = person.surname
                    )
                )
            }
        }
    }

    return arrayOf(personList, cityList, countryList)
}




fun HttpResponseModel.extractCountries() : List<CountryEntity> {
    val list = arrayListOf<CountryEntity>()
    for (i in this.countryList) {
        list.add(
            CountryEntity(name = i.name, countryId = i.countryId.toLong())
        )
    }
    return list
}


fun HttpResponseModel.extractCities() : List<CityEntity> {
    val list = arrayListOf<CityEntity>()
    for (country in this.countryList) {
        for (city in country.cityList) {
            list.add(
                CityEntity(city.cityId.toLong(), country.countryId.toLong(), city.name)
            )
        }
    }
    return list
}


fun HttpResponseModel.extractResidents() : List<ResidenceEntity> {
    val list = arrayListOf<ResidenceEntity>()
    for (country in this.countryList) {
        for (city in country.cityList) {
            for (person in city.peopleList) {
                list.add(
                    ResidenceEntity(
                        humanId = person.humanId.toLong(),
                        cityId = city.cityId.toLong(),
                        name = person.name,
                        surname = person.surname
                    )
                )
            }
        }
    }
    return list
}