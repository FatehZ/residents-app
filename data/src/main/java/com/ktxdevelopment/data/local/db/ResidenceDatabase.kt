package com.ktxdevelopment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidenceEntity

@Database(
    entities = [CountryEntity::class, CityEntity::class, ResidenceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ResidenceDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun cityDao(): CityDao
    abstract fun personDao(): PersonDao
}