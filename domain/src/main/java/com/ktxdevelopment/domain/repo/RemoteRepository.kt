package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.ResponseDataResult
import kotlinx.coroutines.flow.Flow
interface RemoteRepository {

    suspend fun getRemoteData(): Flow<Resource<ResponseDataResult>>
}