package com.ktxdevelopment.residentapp.di

import com.ktxdevelopment.domain.usecase.local.GetCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.GetCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.GetResidentsByCitiesUseCase
import com.ktxdevelopment.domain.usecase.remote.FetchDataUseCase
import com.ktxdevelopment.residentapp.fragments.home.FragmentHomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object PresentationModule {


    @Provides
    fun provideViewModel(
        fetchRemoteDataUseCase: FetchDataUseCase,
        getCitiesUseCase: GetCitiesUseCase,
        getCountriesUseCase: GetCountriesUseCase,
        getResidentsByCitiesUseCase: GetResidentsByCitiesUseCase
    ): FragmentHomeViewModel {
        return FragmentHomeViewModel(fetchRemoteDataUseCase, getCitiesUseCase, getCountriesUseCase, getResidentsByCitiesUseCase)
    }


}