package com.ktxdevelopment.residentapp.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.usecase.remote.FetchDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentHomeViewModel @Inject constructor(
    private var fetchRemoteDataUseCase: FetchDataUseCase,
) : ViewModel() {

    private val countries: MutableLiveData<List<CountryModel>> by lazy { MutableLiveData(null) }
    private val cities: MutableLiveData<List<CityModel>> by lazy { MutableLiveData(null) }



    //    private var localResidentList
//    private val countryList by lazy { mutableStateFlow }
    fun fetchRemoteData() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(Resource.Loading)
            fetchRemoteDataUseCase.invoke()
        }
    }


    fun fetchLocalData() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRemoteDataUseCase.invoke()
        }
    }
}