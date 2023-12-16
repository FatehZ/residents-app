package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.model.HttpResponseModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getRemoteData(): Flow<Resource<HttpResponseModel>>
}