package com.ktxdevelopment.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ktxdevelopment.data.network.model.CityResponse


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["cityId"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cityId")]
)
data class ResidentEntity(
    @PrimaryKey(false)
    val humanId: Long,
    val cityId: Long,
    val name: String,
    val surname: String
)