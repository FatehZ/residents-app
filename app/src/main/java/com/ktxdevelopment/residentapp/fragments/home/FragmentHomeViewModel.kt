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
import com.ktxdevelopment.residentapp.model.CityFilter
import com.ktxdevelopment.residentapp.model.CountryFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentHomeViewModel @Inject constructor(
    private var fetchRemoteDataUseCase: FetchDataUseCase,
    private var getCitiesUseCase: GetCitiesUseCase,
    private var getCountriesUseCase: GetCountriesUseCase,
    private var getResidentsByCitiesUseCase: GetResidentsByCitiesUseCase
) : ViewModel() {

    private val _countries: MutableLiveData<List<CountryFilter>> by lazy { MutableLiveData(arrayListOf()) }
    val countries: LiveData<List<CountryFilter>> = _countries

    private val _cities: MutableLiveData<List<CityFilter>> by lazy { MutableLiveData(arrayListOf()) }
    val cities: LiveData<List<CityFilter>> = _cities


    private val _residents: MutableLiveData<List<ResidentModel>> by lazy { MutableLiveData(arrayListOf()) }
    val residents: LiveData<List<ResidentModel>> = _residents

    private val _apiState: MutableLiveData<Resource<Int>> by lazy { MutableLiveData(Resource.Loading) }   //  We don not need data coming from api, so INT is selected as a random data
    val apiState: LiveData<Resource<Int>> = _apiState

    init {
        observeCountriesCached()
        getResidentsByCities()
    }

    fun fetchRemoteData() {
        viewModelScope.launch(Dispatchers.IO) {
            // We get data from api, and save it to cache, once cache is updated, we will change ui state
            fetchRemoteDataUseCase.invoke().collect {
                _apiState.postValue(it)
            }
        }
    }


    private fun observeCitiesCached() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.invoke().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _cities.postValue(combineCityFilters(it.data, _cities.value!!))
                    }

                    is Resource.Error -> Log.e(
                        "DB_EXCEPTION",
                        "observeCitiesCached: ${it.exception.message}",
                    )

                    is Resource.Loading -> Unit    // Not required in the task
                }
            }
        }
    }


    private fun observeCountriesCached() {
        viewModelScope.launch(Dispatchers.IO) {
            getCountriesUseCase.invoke().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        // if countries are empty fetch remote data, as database is empty
                        if (it.data.isEmpty()) fetchRemoteData()
                        else {
                            // If user refreshes ui, filters are not reset
                            _countries.postValue(combineCountryFilters(it.data, _countries.value!!))
                            observeCitiesCached()
                        }
                    }

                    is Resource.Error -> Log.e(
                        "DB_EXCEPTION",
                        "observeCountriesCached: ${it.exception.message}",
                    )

                    is Resource.Loading -> Unit   // Not required in the task
                }
            }
        }
    }

    private fun getResidentsByCities(cities: List<CityFilter>? = null) {
        cities?.joinToString { it.city.cityId.toString() }?.let { Log.e("LTS_TAG", it) }

        viewModelScope.launch(Dispatchers.IO) {
            getResidentsByCitiesUseCase.invoke(
                cities?.filter { it.selected && it.present }?.map { it.city }
            ).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _residents.postValue(it.data as ArrayList<ResidentModel>)
                    }
                    is Resource.Error -> Log.e(
                        "DB_EXCEPTION",
                        "getResidentsByCities: ${it.exception.message}",
                    )
                    is Resource.Loading -> Unit    // Not required in the task
                }
            }
        }
    }


    // This function enable us to add new countries data to the list, with the way we prefer;
    // Although in our case data is stable, and in 1st if block, we will return old list
    private fun combineCountryFilters(
        newList: List<CountryModel>,
        oldList: List<CountryFilter>
    ): List<CountryFilter> {

        if (oldList == newList) return oldList

        Log.e("LT_TAG", "combineCountryFilters: ")
        val newFilters = ArrayList<CountryFilter>()
        newList.forEach { new ->
            val existingFilter = oldList.find { it.country == new }

            if (existingFilter != null) {
                newFilters.add(existingFilter)
            } else {
                newFilters.add(CountryFilter(new, true))
            }
        }
        return newFilters
    }

    // This function enable us to add new cities data to the list, with the way we prefer;
    // Although in our case data is stable, and in 1st if block, we will return old list
    private fun combineCityFilters(newList: List<CityModel>, oldList: List<CityFilter>): List<CityFilter> {

        if (oldList == newList) return oldList

        val newFilters = ArrayList<CityFilter>()
        newList.forEach { newCityModel ->
            val existingFilter = oldList.find { it.city == newCityModel }

            if (existingFilter != null) {
                newFilters.add(existingFilter)
            } else {
                newFilters.add(CityFilter(newCityModel, present = true, selected = true))
                // if new city is present, we also pass it initially selected
            }
        }

        return newFilters
    }

    //   After update, cities are also updated and residents data is requested
    fun updateCountryFilter(filter: CountryFilter) {
        val currentFilters = _countries.value ?: arrayListOf()

        val existingFilter = currentFilters.find { it.country == filter.country }
        if (existingFilter != null) {
            existingFilter.selected = filter.selected
        }
        _countries.value = currentFilters


        // If country is deselected, no need to deselect cities, just update the present property
        _cities.value = _cities.value!!.map {
            if (it.city.countryId == filter.country.countryId) it.copy(present = filter.selected)
            else it
        }
        getResidentsByCities(_cities.value)
    }

    //   After each update residents data is requested
    fun updateCityFilter(filter: CityFilter) {
        val currentFilters = _cities.value ?: arrayListOf()
        val existingFilter = currentFilters.find { it.city == filter.city }
        if (existingFilter != null) {
            existingFilter.selected = filter.selected
        }
        _cities.value = currentFilters
        getResidentsByCities(currentFilters)
    }
}