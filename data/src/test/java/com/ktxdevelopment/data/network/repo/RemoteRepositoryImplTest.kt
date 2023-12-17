package com.ktxdevelopment.data.network.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.model.CityResponse
import com.ktxdevelopment.data.network.model.CountryResponse
import com.ktxdevelopment.data.network.model.HttpResponseModel
import com.ktxdevelopment.data.network.model.PersonResponse
import com.ktxdevelopment.data.network.service.ApiService
import com.ktxdevelopment.data.util.ApiException
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.model.ResponseDataResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RemoteRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteRepository: RemoteRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        remoteRepository = RemoteRepositoryImpl(apiService)
    }

    @Test
    fun `getRemoteData should return success resource when API call is successful`() = runBlocking {
        // Arrange
        val httpResponseModel = HttpResponseModel(
            countryList = listOf(CountryResponse(name = "Country 1", countryId = 1, cityList = listOf(CityResponse(cityId = 2, name = "City 2", peopleList = listOf(PersonResponse(humanId = 3, name = "Person 3", surname = "Surname 3"))))))
        )


        val expectedResource = Resource.Success(
            ResponseDataResult(
                people = listOf(ResidentModel(humanId = 3, cityId = 2, name = "Person 3", surname = "Surname 3")),
                cities = listOf(CityModel(cityId = 2, countryId = 1, name = "City 2")),
                countries = listOf(CountryModel(countryId = 1, name = "Country 1"))
            )
        )

        val expectedLoadingResponse = Resource.Loading

        val response = Response.success(httpResponseModel)

        Mockito.`when`(apiService.fetchRemoteData()).thenReturn(response)


        // Act
        val result = remoteRepository.getRemoteData().last()
        val loading = remoteRepository.getRemoteData().first()

        // Assert
        assertEquals(expectedResource, result)
        assertEquals(expectedLoadingResponse, loading)
    }

    @Test
    fun `getRemoteData should return error resource when API call is unsuccessful`() = runBlocking {
        // Arrange
        val errorResponse = Response.error<HttpResponseModel>(400, "".toResponseBody())
        val expectedResource = Resource.Error(ApiException(""))
        val expectedLoadingResponse = Resource.Loading

        Mockito.`when`(apiService.fetchRemoteData()).thenReturn(errorResponse)

        // Act
        val result = remoteRepository.getRemoteData().last()
        val loading = remoteRepository.getRemoteData().first()

        // Assert
        assertEquals(expectedResource, result)
        assertEquals(expectedLoadingResponse, loading)
    }
}