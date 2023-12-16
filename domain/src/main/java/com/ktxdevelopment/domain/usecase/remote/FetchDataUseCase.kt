package com.ktxdevelopment.domain.usecase.remote

import android.util.Log
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidenceEntity
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.domain.repo.RemoteRepository
import com.ktxdevelopment.domain.usecase.local.SaveCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.SaveCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.SavePeopleUseCase
import com.ktxdevelopment.domain.util.toEntitiesOfPersonCityCountry
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class FetchDataUseCase @Inject constructor(
    private var repo: RemoteRepository,
    private var saveCitiesUseCase: SaveCitiesUseCase,
    private var saveCountriesUseCase: SaveCountriesUseCase,
    private var savePeopleUseCase: SavePeopleUseCase
) {

    suspend fun fetchDataAndWriteToDb() {
        repo.getRemoteData().collect { resource ->
            when (resource) {
                is Resource.Success -> {
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

    private suspend fun writeToDb(data: HttpResponseModel) {
        saveCountriesUseCase.execute(data.toEntitiesOfPersonCityCountry()[2] as List<CountryEntity>)
        saveCitiesUseCase.execute(data.toEntitiesOfPersonCityCountry()[1] as List<CityEntity>)
        savePeopleUseCase.execute(data.toEntitiesOfPersonCityCountry()[0] as List<ResidenceEntity>)
    }
}