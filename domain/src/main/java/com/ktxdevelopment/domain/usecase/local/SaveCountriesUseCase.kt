package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke(countries: List<CountryModel>) {
        localRepository.clearCountries()
        localRepository.saveCountries(countries)
    }
}



