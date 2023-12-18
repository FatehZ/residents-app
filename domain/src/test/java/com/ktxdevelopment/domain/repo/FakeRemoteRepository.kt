package com.ktxdevelopment.domain.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.ResponseDataResult
import com.ktxdevelopment.domain.util.TestData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteRepository : RemoteRepository {
    private var responseData: ResponseDataResult? = null

    override suspend fun getRemoteData(): Flow<Resource<ResponseDataResult>> = flow {
        emit(Resource.Loading)
        delay(500)
        try {
            emit(Resource.Success(responseData!!))
        }catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


    fun saveData(response: ResponseDataResult) {
        responseData = response
    }
}