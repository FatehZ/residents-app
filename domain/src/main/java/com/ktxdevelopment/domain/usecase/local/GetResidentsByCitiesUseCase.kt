package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.LocalRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetResidentsByCitiesUseCase @Inject constructor(private var repo: LocalRepository) {

    suspend operator fun invoke(cities: ArrayList<CityModel>? = null) = flow {
        emit(Resource.Loading)
        try {

            //  if cities is null, get all residents.
            //  Instead, sending all city list was also an option.
            //  But, as we need all people at first, no need to add weight to database to check for all cities in all people, add we don't need to wait for getting city list to query people.

            if (cities == null) {
                emit(Resource.Success(repo.getAllResidents()))
            }else {
                val cityIds = cities.map { it.cityId }
                emit(Resource.Success(repo.getResidents(cityIds)))
            }
        }catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}