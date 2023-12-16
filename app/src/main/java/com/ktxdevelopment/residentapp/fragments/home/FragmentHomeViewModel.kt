package com.ktxdevelopment.residentapp.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.usecase.local.GetCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.GetCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.GetResidentsByCitiesUseCase
import com.ktxdevelopment.domain.usecase.remote.FetchDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentHomeViewModel @Inject constructor(
    private var fetchRemoteDataUseCase: FetchDataUseCase,
    private var getCitiesUseCase: GetCitiesUseCase,
    private var getCountriesUseCase: GetCountriesUseCase,
    private var getResidentsByCitiesUseCase: GetResidentsByCitiesUseCase
) : ViewModel() {

    private val _countries: MutableLiveData<ArrayList<CountryModel>> by lazy { MutableLiveData(arrayListOf()) }
    val countries: LiveData<ArrayList<CountryModel>> = _countries

    private val _cities: MutableLiveData<ArrayList<CityModel>> by lazy { MutableLiveData(arrayListOf()) }
    val cities: LiveData<ArrayList<CityModel>> = _cities

    private val _residents: MutableLiveData<ArrayList<ResidentModel>> by lazy { MutableLiveData(arrayListOf()) }
    val residents: LiveData<ArrayList<ResidentModel>> = _residents

    // Only Id of city and countries could be saved, but data in City and Country models is not that big,
    //      so, there won't be any performance problem, it is simpler in our case

    private val _selectedCountries: MutableLiveData<ArrayList<CountryModel>> by lazy { MutableLiveData(arrayListOf()) }
    val selectedCountries: LiveData<ArrayList<CountryModel>> = _selectedCountries

    private val _selectedCities: MutableLiveData<ArrayList<CityModel>> by lazy { MutableLiveData(arrayListOf()) }
    val selectedCities: LiveData<ArrayList<CityModel>> = _selectedCities

    init {
        fetchRemoteData()
        observeCountriesCached()
        observeCitiesCached()

        // Once cities are obtained, fetch people from cache. No city passed, so it will fetch all
        getResidentsByCities()
    }

    fun fetchRemoteData() {
        viewModelScope.launch(Dispatchers.IO) {
            // We get data from api, and save it to cache, once cache is updated, we will change ui state
            fetchRemoteDataUseCase.invoke()
        }
    }


    private fun observeCitiesCached() {
        viewModelScope.launch {
            getCitiesUseCase.invoke().flowOn(Dispatchers.IO).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        // If user refreshes ui, filters are reset

                        _cities.postValue(it.data as ArrayList<CityModel>)
                        _selectedCities.postValue(it.data as ArrayList<CityModel>)
                    }
                    is Resource.Error -> Log.e("DB_EXCEPTION", "observeCitiesCached: ${it.exception.message}",)
                    is Resource.Loading -> Unit    // Not required in the task
                }
            }
        }
    }

    private fun observeCountriesCached() {
        viewModelScope.launch {
            getCountriesUseCase.invoke().flowOn(Dispatchers.IO).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        // if countries are empty fetch remote data, as database is empty
                        if (it.data.isEmpty()) fetchRemoteData()
                        else {
                            // If user refreshes ui, filters are reset

                            _countries.postValue(it.data as ArrayList<CountryModel>)
                            _selectedCountries.value!!.clear()
                            _selectedCountries.value!!.addAll(it.data.toMutableSet())
                        }
                    }
                    is Resource.Error -> Log.e("DB_EXCEPTION", "observeCountriesCached: ${it.exception.message}",)
                    is Resource.Loading -> Unit   // Not required in the task
                }
            }
        }
    }

    private fun getResidentsByCities(cities: ArrayList<CityModel>? = null) {
        viewModelScope.launch {
            getResidentsByCitiesUseCase.invoke(cities).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _residents.postValue(it.data as ArrayList<ResidentModel>)
                    }
                    is Resource.Error -> Log.e("DB_EXCEPTION", "getResidentsByCities: ${it.exception.message}",)
                    is Resource.Loading -> Unit    // Not required in the task
                }
            }
        }
    }
}