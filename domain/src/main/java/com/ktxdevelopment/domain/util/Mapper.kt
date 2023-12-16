package com.ktxdevelopment.domain.util
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidentEntity
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel


//  For better performance this way is preferred, although a bit confusing.
//  Returning 3 list in a hashmap with keys would be better, but this way is simpler.
fun HttpResponseModel.toEntitiesOfPersonCityCountry() : Array<List<Any>> {
    val personList = arrayListOf<ResidentEntity>()
    val cityList = arrayListOf<CityEntity>()
    val countryList = arrayListOf<CountryEntity>()

    this.countryList.forEach { country ->
        countryList.add(CountryEntity(name = country.name, countryId = country.countryId))

        country.cityList.forEach { city ->
            cityList.add(CityEntity(city.cityId, country.countryId, city.name))

            city.peopleList.forEach { person ->
                personList.add(
                    ResidentEntity(
                        humanId = person.humanId,
                        cityId = city.cityId,
                        name = person.name,
                        surname = person.surname
                    )
                )
            }
        }
    }

    return arrayOf(personList, cityList, countryList)
}


fun List<CountryEntity>.toDomain() = map { it.toDomain() }
fun List<CityEntity>.toDomain() = map { it.toDomain() }
fun List<ResidentEntity>.toDomain() = map { it.toDomain() }

fun CountryEntity.toDomain() = CountryModel(countryId = countryId, name = name)
fun CityEntity.toDomain() = CityModel(countryId = countryId, name = name, cityId = cityId)
fun ResidentEntity.toDomain() = ResidentModel(humanId = humanId, cityId = cityId, name = name, surname = surname)



//fun HttpResponseModel.extractCountries() : List<CountryEntity> {
//    val list = arrayListOf<CountryEntity>()
//    for (i in this.countryList) {
//        list.add(
//            CountryEntity(name = i.name, countryId = i.countryId.toLong())
//        )
//    }
//    return list
//}
//
//
//fun HttpResponseModel.extractCities() : List<CityEntity> {
//    val list = arrayListOf<CityEntity>()
//    for (country in this.countryList) {
//        for (city in country.cityList) {
//            list.add(
//                CityEntity(city.cityId.toLong(), country.countryId.toLong(), city.name)
//            )
//        }
//    }
//    return list
//}
//
//
//fun HttpResponseModel.extractResidents() : List<ResidentEntity> {
//    val list = arrayListOf<ResidentEntity>()
//    for (country in this.countryList) {
//        for (city in country.cityList) {
//            for (person in city.peopleList) {
//                list.add(
//                    ResidentEntity(
//                        humanId = person.humanId.toLong(),
//                        cityId = city.cityId.toLong(),
//                        name = person.name,
//                        surname = person.surname
//                    )
//                )
//            }
//        }
//    }
//    return list
//}