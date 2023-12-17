package com.ktxdevelopment.residentapp.model

import com.ktxdevelopment.domain.model.CountryModel

data class CountryFilter(var country: CountryModel, var selected: Boolean = true) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CountryFilter
        return country.countryId == other.country.countryId && selected == other.selected
//                && country.name == other.name    // no need currently
    }

    override fun hashCode(): Int {
        var result = country.hashCode()
        result = 31 * result + selected.hashCode()
        return result
    }
}