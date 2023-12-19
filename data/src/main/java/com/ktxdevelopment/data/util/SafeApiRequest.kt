package com.ktxdevelopment.data.util

import com.ktxdevelopment.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException


suspend fun <T : Any, R : Any> safeApiRequest(call: suspend () -> Response<T>, mapper: ((T) -> R)): Flow<Resource<R>> {
    return flow {
        emit(Resource.Loading)
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                if (response.body() != null) emit(Resource.Success(mapper(response.body()!!)))
                else emit(Resource.Error(ApiException("Something went wrong")))
            } else {
                if (response.code() == 408) emit(Resource.Error(ConnectException())) // no network
                else {
                    val responseErr = response.errorBody()?.string()
                    val message = StringBuilder()
                    responseErr.let {
                        try {
                            message.append(it?.let { mes -> JSONObject(mes).getString("error") })
                        } catch (_: JSONException) { }
                    }
                    emit(Resource.Error(ApiException(message.toString())))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}
