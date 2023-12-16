package com.ktxdevelopment.data.network.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.service.ApiService
import com.ktxdevelopment.data.util.safeApiRequest
import com.ktxdevelopment.data.util.toModelsOfPersonCityCountry
import com.ktxdevelopment.domain.repo.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {
    override suspend fun getRemoteData(): Flow<Resource<Array<List<Any>>>> {
        return safeApiRequest {
            apiService.fetchRemoteData()
        }.map {
            when (it) {
                is Resource.Success -> {
                    Resource.Success(it.data.toModelsOfPersonCityCountry())
                }

                is Resource.Error -> it
                is Resource.Loading -> it
            }
        }
    }
}