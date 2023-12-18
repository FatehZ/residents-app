package com.ktxdevelopment.domain.util

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



        val exCountryList = arrayListOf(CountryModel(1L, "Country 1"), CountryModel(2L, "Country 2"))
        val exCityList = arrayListOf(CityModel(10L, 1L,"City 10"), CityModel(21L, 2L,"City 20"))
        val exResidentList = arrayListOf(
            ResidentModel(30L, "Person 30",10L,"Surname 1"),
            ResidentModel(40L, "Person 40",10L,"Surname 2"),
            ResidentModel(50L, "Person 50",20L,"Surname 3"),
            ResidentModel(60L, "Person 60",20L,"Surname 4"),
            )
        val exResponseData = ResponseDataResult(exCountryList, exCityList, exResidentList)
    }
}