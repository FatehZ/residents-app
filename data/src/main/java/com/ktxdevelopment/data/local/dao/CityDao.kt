package com.ktxdevelopment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ktxdevelopment.data.local.model.CityEntity

@Dao
interface CityDao {
    
    @Query("SELECT * FROM CityEntity")
    fun getAllCities(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<CityEntity>)
}