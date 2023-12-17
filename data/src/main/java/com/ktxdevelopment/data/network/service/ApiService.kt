package com.ktxdevelopment.data.network.service

import com.ktxdevelopment.data.network.model.HttpResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun fetchRemoteData(@Url url: String = "/tayqa/tiger/api/development/test/TayqaTech/getdata/"): Response<HttpResponseModel>

}