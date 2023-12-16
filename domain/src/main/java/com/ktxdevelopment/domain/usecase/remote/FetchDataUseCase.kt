package com.ktxdevelopment.domain.usecase.remote

import android.util.Log
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.RemoteRepository
import com.ktxdevelopment.domain.usecase.local.SaveCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.SaveCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.SavePeopleUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FetchDataUseCase @Inject constructor(
    private var repo: RemoteRepository,
    private var saveCitiesUseCase: SaveCitiesUseCase,
    private var saveCountriesUseCase: SaveCountriesUseCase,
    private var savePeopleUseCase: SavePeopleUseCase
) {

    suspend operator fun invoke() {
        repo.getRemoteData().collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Assuming data cannot be empty
                    writeToDb(resource.data)
                }
                is Resource.Error -> {
                    Log.e("API_ERROR", resource.exception.message.toString())
                }
                is Resource.Loading -> {
                    // No need to write something, as data is not returned to UI layer in our case
                }
            }
        }
    }

    private suspend fun writeToDb(data: Array<List<Any>>) {
        saveCountriesUseCase.execute(data[2] as List<CountryModel>)
        saveCitiesUseCase.execute(data[1] as List<CityModel>)
        savePeopleUseCase.execute(data[0] as List<ResidentModel>)
    }
}