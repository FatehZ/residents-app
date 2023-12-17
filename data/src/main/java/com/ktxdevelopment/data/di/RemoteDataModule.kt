package com.ktxdevelopment.data.di

import com.ktxdevelopment.common.Constant
import com.ktxdevelopment.data.network.repo.RemoteRepositoryImpl
import com.ktxdevelopment.data.network.service.ApiService
import com.ktxdevelopment.domain.repo.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@InstallIn(SingletonComponent::class)
@Module
object RemoteDataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
    }


    @Provides
    fun client() = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    @Provides
    fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRemoteRepository(apiService: ApiService): RemoteRepository {
        return RemoteRepositoryImpl(apiService = apiService)
    }
}