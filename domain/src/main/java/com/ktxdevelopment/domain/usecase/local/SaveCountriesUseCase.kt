package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend fun execute(countries: List<CountryEntity>) {
        localRepository.saveCountries(countries)
    }
}



