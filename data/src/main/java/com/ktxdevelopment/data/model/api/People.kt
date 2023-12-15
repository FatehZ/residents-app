package com.ktxdevelopment.data.model.api

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("humanId")
    @PrimaryKey(false)
    val humanId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String
)