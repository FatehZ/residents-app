package com.ktxdevelopment.data.di

import android.content.Context
import androidx.room.Room
import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.local.db.ResidentDatabase
import com.ktxdevelopment.data.local.repo.LocalRepositoryImpl
import com.ktxdevelopment.domain.repo.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object LocalDataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        ResidentDatabase::class.java,
        "resident_db"
    ).build()


    @Provides
    fun provideLocalRepository(countryDao: CountryDao, cityDao: CityDao, personDao: PersonDao): LocalRepository {
        return LocalRepositoryImpl(countryDao, personDao, cityDao)
    }

    @Singleton
    @Provides
    fun provideCountryDao(db: ResidentDatabase) = db.countryDao()

    @Singleton
    @Provides
    fun provideCityDao(db: ResidentDatabase) = db.cityDao()

    @Singleton
    @Provides
    fun providePersonDao(db: ResidentDatabase) = db.personDao()
}