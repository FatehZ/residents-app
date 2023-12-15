package com.ktxdevelopment.data.model.api

import com.google.gson.annotations.SerializedName

data class HttpResponseModel(
    @SerializedName("countryList")
    val countryList: List<Country>
)