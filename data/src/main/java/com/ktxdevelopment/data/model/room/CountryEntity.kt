package com.ktxdevelopment.data.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CountryEntity(
    @PrimaryKey(autoGenerate = false)
    val countryId: Int,
    val cityList: List<CityEntity>,
    val name: String
)