package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend fun execute(countries: List<CountryModel>) {
        localRepository.saveCountries(countries)
    }
}



