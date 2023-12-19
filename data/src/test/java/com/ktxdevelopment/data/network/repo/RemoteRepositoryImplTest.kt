package com.ktxdevelopment.data.network.repo

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.data.network.service.FakeApiService
import com.ktxdevelopment.data.network.util.TestData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

class RemoteRepositoryImplTest {

    private lateinit var apiService: FakeApiService

    private lateinit var remoteRepository: RemoteRepositoryImpl

    @Before
    fun setup() {
        apiService = FakeApiService()
        remoteRepository = RemoteRepositoryImpl(apiService)
    }

    @Test
    fun `getRemoteData should return success resource when API call is successful`() = runBlocking {

        val result = remoteRepository.getRemoteData().last()
        val loading = remoteRepository.getRemoteData().first()

        assertEquals(Resource.Success(TestData.exResponseData), result)
        assertEquals(Resource.Loading, loading)
    }

    @Test
    fun `getRemoteData should return error resource when API call is unsuccessful`() = runBlocking {

        apiService.setMustReturnNetworkError(true)
        val result = remoteRepository.getRemoteData().last()
        val loading = remoteRepository.getRemoteData().first()

        assertTrue(result is Resource.Error && result.exception is ConnectException)
        assertEquals(Resource.Loading, loading)
    }
}