package com.ktxdevelopment.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ktxdevelopment.data.network.model.CityResponse


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CityResponse::class,
            parentColumns = ["cityId"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ResidenceEntity(
    @PrimaryKey(false)
    val humanId: Long,
    val cityId: Long,
    val name: String,
    val surname: String
)