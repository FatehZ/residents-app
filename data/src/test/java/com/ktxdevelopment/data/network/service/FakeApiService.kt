package com.ktxdevelopment.data.network.service

import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.data.network.util.TestData
import com.ktxdevelopment.data.util.ApiException
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.Response

class FakeApiService  : ApiService{

    private var mustReturnNetworkError = false

    fun setMustReturnNetworkError(value: Boolean) { mustReturnNetworkError = value }

    override suspend fun fetchRemoteData(url: String): Response<HttpResponseModel> {
        delay(200)
        if (mustReturnNetworkError) return Response.error(408, EMPTY_RESPONSE)
        return Response.success(TestData.httpResponseData)
    }
}