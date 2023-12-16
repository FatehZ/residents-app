package com.ktxdevelopment.data.network.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ktxdevelopment.data.local.model.CityEntity

data class CountryResponse(
    @SerializedName("countryId")
    @PrimaryKey(autoGenerate = false)
    val countryId: Long,
    @SerializedName("cityList")
    val cityList: List<CityResponse>,
    @SerializedName("name")
    val name: String
)