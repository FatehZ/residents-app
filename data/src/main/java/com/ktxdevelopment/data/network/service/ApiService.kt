package com.ktxdevelopment.data.network.service

import com.ktxdevelopment.data.network.model.HttpResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET
    suspend fun fetchRemoteData(): Response<HttpResponseModel>

}