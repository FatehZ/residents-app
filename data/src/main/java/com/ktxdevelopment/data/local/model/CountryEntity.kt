package com.ktxdevelopment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class CountryEntity(
    @PrimaryKey(autoGenerate = false)
    val countryId: Long,
    val name: String
)