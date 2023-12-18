package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SaveCitiesUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke(cities: List<CityModel>) {
        localRepository.clearCities()   //   Some data might have been deleted from the server. So, db must be cleared initially
        localRepository.saveCities(cities)
    }
}