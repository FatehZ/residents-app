package com.ktxdevelopment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ktxdevelopment.data.local.model.ResidenceEntity

@Dao
interface PersonDao {
    
    @Query("SELECT * FROM ResidenceEntity")
    fun getAllPeople(): List<ResidenceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(people: List<ResidenceEntity>)

}