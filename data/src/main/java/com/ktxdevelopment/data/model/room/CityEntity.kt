package com.ktxdevelopment.data.model.room

import androidx.room.PrimaryKey

data class CityEntity(
    @PrimaryKey(false)
    val cityId: Int,
    val name: String,
    val peopleList: List<PeopleEntity>
)