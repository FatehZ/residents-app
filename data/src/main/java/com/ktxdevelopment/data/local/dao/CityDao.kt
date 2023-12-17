package com.ktxdevelopment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ktxdevelopment.data.local.model.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    
    @Query("SELECT * FROM CityEntity")
    fun getAllCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)
}