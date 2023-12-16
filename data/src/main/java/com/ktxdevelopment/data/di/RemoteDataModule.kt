package com.ktxdevelopment.data.di

import com.ktxdevelopment.common.Constant
import com.ktxdevelopment.data.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(SingletonComponent::class)
@Module
object RemoteDataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
//
//    @Provides
//    fun provideGetBlogsRepository(apiService: ApiService): GetBlogsRepository {
//        return GetBlogsRepositoryImpl(apiService = apiService)
//    }
//
//    @Provides
//    fun provideDataBase(@ApplicationContext context: Context): BlogDataBase {
//        return BlogDataBase.getInstance(context)
//    }
//
//    @Provides
//    fun provideDAO(blogDataBase: BlogDataBase): BlogDAO {
//        return blogDataBase.getBlogDAO()
//    }
//
//
//    @Provides
//    fun provideGetPagerRepo(apiService: ApiService): GetPagerBlogsRepo {
//        return GetPagerBlogsRepoImpl(apiService)
//    }


}