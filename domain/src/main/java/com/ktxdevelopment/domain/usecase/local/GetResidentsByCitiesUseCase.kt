package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.LocalRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetResidentsByCitiesUseCase @Inject constructor(private var repo: LocalRepository) {

    suspend operator fun invoke(cities: List<CityModel>? = null) = flow {
        emit(Resource.Loading)
        //  if cities is null, get all residents.
        //  Instead, sending all city list was also an option.
        //  But, as we need all people at first, no need to add weight to database to check for all cities in all people, add we don't need to wait for getting city list to query people.

        if (cities == null) {
            repo.getAllResidents()
                .map { Resource.Success(it) }
                .catch { exception -> emit(Resource.Error(exception)) }
                .onStart { emit(Resource.Loading) }
                .collect { emit(it) }
        } else {
            val cityIds = cities.map { it.cityId }
            repo.getResidents(cityIds)
                .map { Resource.Success(it) }
                .catch { exception -> emit(Resource.Error(exception)) }
                .onStart { emit(Resource.Loading) }
                .collect { emit(it) }
        }
    }
}