package com.ktxdevelopment.domain.usecase.remote

import android.util.Log
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.ResponseDataResult
import com.ktxdevelopment.domain.repo.RemoteRepository
import com.ktxdevelopment.domain.usecase.local.SaveCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.SaveCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.SavePeopleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchDataUseCase @Inject constructor(
    private var repo: RemoteRepository,
    private var saveCitiesUseCase: SaveCitiesUseCase,
    private var saveCountriesUseCase: SaveCountriesUseCase,
    private var savePeopleUseCase: SavePeopleUseCase
) {

    suspend operator fun invoke(): Flow<Resource<Int>> = flow {
        repo.getRemoteData().collect { resource ->
            when (resource) {
                is Resource.Success -> {             //    Assuming data cannot be empty
                    emit(Resource.Success(0))   //   NO need to pass data yo UI, as it is handled locally
                    writeToDb(resource.data)
                }

                is Resource.Error -> {
                    emit(resource)
                    Log.e("API_ERROR", resource.exception.message.toString())
                }

                is Resource.Loading -> emit(resource)    // No need to write something, as data is not returned to UI layer in our case
            }
        }
    }

    private suspend fun writeToDb(data: ResponseDataResult) {
        saveCountriesUseCase.execute(data.countries)
        saveCitiesUseCase.execute(data.cities)
        savePeopleUseCase.execute(data.people)
    }
}