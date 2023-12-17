package com.ktxdevelopment.data.network.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.service.ApiService
import com.ktxdevelopment.data.util.safeApiRequest
import com.ktxdevelopment.data.util.toModelsOfPersonCityCountry
import com.ktxdevelopment.domain.model.ResponseDataResult
import com.ktxdevelopment.domain.repo.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {
    override suspend fun getRemoteData(): Flow<Resource<ResponseDataResult>> {
        return safeApiRequest(
            call = { apiService.fetchRemoteData() },
            mapper = { it.toModelsOfPersonCityCountry() }
        )
    }
}