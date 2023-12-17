package com.ktxdevelopment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ktxdevelopment.data.local.model.ResidentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    
    @Query("SELECT * FROM ResidentEntity")
    fun getAllPeople(): Flow<List<ResidentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(people: List<ResidentEntity>)

    @Query("SELECT * FROM ResidentEntity WHERE cityId IN (:cities)")
    fun getPeopleByCityIds(cities: List<Long>): Flow<List<ResidentEntity>>

}