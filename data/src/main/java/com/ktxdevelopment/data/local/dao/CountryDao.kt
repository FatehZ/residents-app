package com.ktxdevelopment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ktxdevelopment.data.local.model.CountryEntity

@Dao
interface CountryDao {

    @Query("SELECT * FROM CountryEntity")
    fun getAllCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<CountryEntity>)

}