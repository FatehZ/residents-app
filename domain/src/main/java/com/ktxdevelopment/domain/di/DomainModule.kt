package com.ktxdevelopment.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {



//    @Provides
//    fun provideGetBlogsUseCase(getBlogsRepository: GetBlogsRepository):GetBlogsUseCase{
//        return GetBlogsUseCase(getBlogsRepository)
//    }

}