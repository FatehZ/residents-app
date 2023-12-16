package com.ktxdevelopment.data.network.model

import com.google.gson.annotations.SerializedName

data class HttpResponseModel(
    @SerializedName("countryList")
    val countryList: List<CountryResponse>
)