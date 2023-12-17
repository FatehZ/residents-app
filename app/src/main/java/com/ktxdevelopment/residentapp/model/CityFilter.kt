package com.ktxdevelopment.residentapp.model

import com.ktxdevelopment.domain.model.CityModel

data class CityFilter(
    var city: CityModel,
    var present: Boolean,
    var selected: Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CityModel
        return city.cityId == other.cityId && city.name == other.name && city.countryId == other.countryId
    }

    override fun hashCode(): Int {
        var result = city.hashCode()
        result = 31 * result + selected.hashCode()
        return result
    }
}