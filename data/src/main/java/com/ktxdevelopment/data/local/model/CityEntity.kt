package com.ktxdevelopment.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["countryId"],
            childColumns = ["countryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("countryId")]
)
data class CityEntity(
    @PrimaryKey(false)
    val cityId: Long,
    val countryId: Long,
    val name: String,
)