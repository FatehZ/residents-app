package com.ktxdevelopment.data.network.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.data.network.service.ApiService
import com.ktxdevelopment.data.util.safeApiRequest
import com.ktxdevelopment.domain.repo.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {
    override suspend fun getRemoteData(): Flow<Resource<HttpResponseModel>> {
        return safeApiRequest {
            apiService.fetchRemoteData()
        }
    }

}