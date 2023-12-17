package com.ktxdevelopment.data.util
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidentEntity
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.model.ResponseDataResult



fun HttpResponseModel.toModelsOfPersonCityCountry() : ResponseDataResult {
    val peopleList = arrayListOf<ResidentModel>()
    val cityList = arrayListOf<CityModel>()
    val countryList = arrayListOf<CountryModel>()

    this.countryList.forEach { country ->
        countryList.add(CountryModel(name = country.name, countryId = country.countryId))

        country.cityList.forEach { city ->
            cityList.add(CityModel(city.cityId, country.countryId, city.name))

            city.peopleList.forEach { person ->
                peopleList.add(
                    ResidentModel(
                        humanId = person.humanId,
                        cityId = city.cityId,
                        name = person.name,
                        surname = person.surname
                    )
                )
            }
        }
    }

    return ResponseDataResult(countryList, cityList, peopleList)
}




fun List<CountryEntity>.toCountryDomain() = map { it.toDomain() }
fun List<CityEntity>.toCityDomain() = map { it.toDomain() }
fun List<ResidentEntity>.toResidentDomain() = map { it.toDomain() }

fun CountryEntity.toDomain() = CountryModel(countryId = countryId, name = name)
fun CityEntity.toDomain() = CityModel(countryId = countryId, name = name, cityId = cityId)
fun ResidentEntity.toDomain() = ResidentModel(humanId = humanId, cityId = cityId, name = name, surname = surname)


// Various names instead of toEntity() and toDomain(), because List extension functions can't have same name
fun List<CountryModel>.toCountryEntity() = map { it.toEntity() }
fun List<CityModel>.toCityEntity() = map { it.toEntity() }
fun List<ResidentModel>.toResidentEntity() = map { it.toEntity() }

fun CountryModel.toEntity() = CountryEntity(countryId = countryId, name = name)
fun CityModel.toEntity() = CityEntity(countryId = countryId, name = name, cityId = cityId)
fun ResidentModel.toEntity() = ResidentEntity(humanId = humanId, cityId = cityId, name = name, surname = surname)



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