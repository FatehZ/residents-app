package com.ktxdevelopment.data.network.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("humanId")
    @PrimaryKey(false)
    val humanId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String
)