package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val repository: LocalRepository) {

    operator fun invoke() : Flow<Resource<List<CityModel>>> = flow {
        repository.getCities()
            .map { Resource.Success(it) }
            .catch { exception -> emit(Resource.Error(exception)) }
            .onStart { emit(Resource.Loading) }
            .collect { emit(it) }
    }
}