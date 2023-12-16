package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val repository: LocalRepository) {

    operator fun invoke() : Flow<Resource<List<CityModel>>> = flow {
        emit(Resource.Loading)
        try {
            val data = repository.getCities()
            emit(Resource.Success(data = data))
        }catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}