package com.ktxdevelopment.data.util

import com.ktxdevelopment.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


suspend fun <T : Any, R : Any> safeApiRequest(call: suspend () -> Response<T>, mapper: ((T) -> R)): Flow<Resource<R>> {
    return flow {
        emit(Resource.Loading)
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                emit(Resource.Success(mapper(response.body()!!)))
            } else {
                val responseErr = response.errorBody()?.string()
                val message = StringBuilder()
                responseErr.let {
                    try {
                        message.append(it?.let { mes -> JSONObject(mes).getString("error") })
                    } catch (_: JSONException) {
                    }
                }
                Resource.Error(ApiException(message.toString()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}
