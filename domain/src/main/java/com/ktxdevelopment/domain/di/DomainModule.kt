package com.ktxdevelopment.domain.di

import com.ktxdevelopment.domain.repo.LocalRepository
import com.ktxdevelopment.domain.repo.RemoteRepository
import com.ktxdevelopment.domain.usecase.local.GetCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.GetCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.GetResidentsByCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.SaveCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.SaveCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.SavePeopleUseCase
import com.ktxdevelopment.domain.usecase.remote.FetchDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {


    @Provides
    fun provideGetCitiesUseCase(repository: LocalRepository): GetCitiesUseCase {
        return GetCitiesUseCase(repository)
    }


    @Provides
    fun provideGetCountriesUseCase(repository: LocalRepository): GetCountriesUseCase {
        return GetCountriesUseCase(repository)
    }


    @Provides
    fun provideGetResidentsUseCase(repository: LocalRepository): GetResidentsByCitiesUseCase {
        return GetResidentsByCitiesUseCase(repository)
    }


    @Provides
    fun provideSaveResidentsUseCase(repository: LocalRepository): SavePeopleUseCase {
        return SavePeopleUseCase(repository)
    }

    @Provides
    fun provideSaveCities(repository: LocalRepository): SaveCitiesUseCase {
        return SaveCitiesUseCase(repository)
    }



    @Provides
    fun provideSaveCountries(repository: LocalRepository): SaveCountriesUseCase {
        return SaveCountriesUseCase(repository)
    }

    @Provides
    fun provideFetchRemoteDataUseCase(
        repository: RemoteRepository,
        saveCitiesUseCase: SaveCitiesUseCase,
        saveCountriesUseCase: SaveCountriesUseCase,
        savePeopleUseCase: SavePeopleUseCase
    ): FetchDataUseCase {
        return FetchDataUseCase(repository, saveCitiesUseCase, saveCountriesUseCase, savePeopleUseCase)
    }
}