package com.ktxdevelopment.data.model.room

import androidx.room.PrimaryKey

data class PeopleEntity(
    @PrimaryKey(false)
    val humanId: Int,
    val name: String,
    val surname: String
)