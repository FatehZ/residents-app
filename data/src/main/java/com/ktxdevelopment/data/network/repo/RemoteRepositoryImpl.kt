package com.ktxdevelopment.data.network.repo

import android.util.Log
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.data.network.service.ApiService
import com.ktxdevelopment.data.util.safeApiRequest
import com.ktxdevelopment.data.util.toModelsOfPersonCityCountry
import com.ktxdevelopment.domain.repo.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getRemoteData(): Flow<Resource<Array<List<Any>>>> {
        return safeApiRequest(
            call = { apiService.fetchRemoteData() },
            mapper = { it.toModelsOfPersonCityCountry() }
        )
    }
}