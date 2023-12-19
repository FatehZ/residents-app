package com.ktxdevelopment.data.network.util

import com.ktxdevelopment.data.network.model.CityResponse
import com.ktxdevelopment.data.network.model.CountryResponse
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.data.network.model.PersonResponse
import com.ktxdevelopment.data.util.toModelsOfPersonCityCountry
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.model.ResponseDataResult

class TestData {
    companion object {
        val countryList = arrayListOf(CountryModel(1L, "Country 1"))
        val cityList = arrayListOf(CityModel(2L, 1L,"City 1"))
        val residentList = arrayListOf(ResidentModel(3L, "Person 1",2L,"Surname 1"))

        val responseData = ResponseDataResult(countryList, cityList, residentList)



        val httpResponseData = HttpResponseModel(listOf(
            CountryResponse(1L, listOf(
                CityResponse(10L, "City 10", listOf(PersonResponse(100L, "Person 100", "Surname 1"), PersonResponse(110L, "Person 110", "Surname 2"))),
                CityResponse(20L, "City 20", listOf(PersonResponse(120L, "Person 120", "Surname 3"), PersonResponse(130L, "Person 130", "Surname 4"))),
            ), "Country 1"),

            CountryResponse(2L, listOf(
                CityResponse(30L, "City 30", listOf(PersonResponse(140L, "Person 140", "Surname 5"), PersonResponse(150L, "Person 150", "Surname 6"))),
                CityResponse(40L, "City 40", listOf(PersonResponse(160L, "Person 160", "Surname 7"), PersonResponse(170L, "Person 170", "Surname 8"))),
            ), "Country 2")
        ))

        val exResponseData: ResponseDataResult = httpResponseData.toModelsOfPersonCityCountry()

        val exCountryList = exResponseData.countries
        val exCityList = exResponseData.cities
        val exResidentList = exResponseData.people

//        val exCountryList = arrayListOf(CountryModel(1L, "Country 1"), CountryModel(2L, "Country 2"))
//
//        val exCityList = arrayListOf(
//            CityModel(10L, 1L,"City 10"),
//            CityModel(20L, 1L,"City 20"),
//            CityModel(30L, 2L,"City 30"),
//            CityModel(40L, 2L,"City 40"),
//        )
//
//        val exResidentList = arrayListOf(
//            ResidentModel(100L, "Person 100",10L,"Surname 1"),
//            ResidentModel(110L, "Person 110",10L,"Surname 2"),
//            ResidentModel(120L, "Person 120",20L,"Surname 3"),
//            ResidentModel(130L, "Person 130",20L,"Surname 4"),
//            ResidentModel(140L, "Person 140",30L,"Surname 5"),
//            ResidentModel(150L, "Person 150",30L,"Surname 6"),
//            ResidentModel(160L, "Person 160",40L,"Surname 7"),
//            ResidentModel(170L, "Person 170",40L,"Surname 8"),
//        )
//
//        val exResponseData = ResponseDataResult(exCountryList, exCityList, exResidentList)
    }
}