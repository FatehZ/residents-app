package com.ktxdevelopment.data.di

import android.content.Context
import androidx.room.Room
import com.ktxdevelopment.data.local.db.ResidenceDatabase
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
        ResidenceDatabase::class.java,
        "residence_db"
    ).build()


    @Singleton
    @Provides
    fun provideCountryDao(db: ResidenceDatabase) = db.countryDao()

    @Singleton
    @Provides
    fun provideCityDao(db: ResidenceDatabase) = db.cityDao()

    @Singleton
    @Provides
    fun providePersonDao(db: ResidenceDatabase) = db.personDao()
}