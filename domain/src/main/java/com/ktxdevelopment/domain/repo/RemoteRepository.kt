package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.common.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getRemoteData(): Flow<Resource<Array<List<Any>>>>
}