package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SaveCitiesUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend fun execute(cities: List<CityModel>) {
        localRepository.saveCities(cities)
    }
}