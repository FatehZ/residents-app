package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.repo.LocalRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetResidentsByCitiesUseCase @Inject constructor(private var repo: LocalRepository) {

    suspend operator fun invoke(cityIds: List<String>) = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repo.getResidents(cityIds)))
        }catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}