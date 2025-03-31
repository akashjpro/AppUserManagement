package com.example.apptest.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <T> networkBoundResource(
    crossinline fetchRemote: suspend () -> T
): Flow<Resource<T>> = flow {
    emit(Resource.loading())  // Emit loading state

    try {
        val response = fetchRemote()
        emit(Resource.success(response)) // Emit success state
    } catch (e: UnknownHostException) {
        emit(Resource.error(null, ErrorCode.CONNECT_ERROR)) // No internet connection
    } catch (e: SocketTimeoutException) {
        emit(Resource.error(null, ErrorCode.TIMEOUT)) // Timeout
    } catch (e: HttpException) {
        emit(Resource.error(null, ErrorCode.HTTP_ERROR)) // Server error
    } catch (e: Exception) {
        emit(Resource.error(null, ErrorCode.UNKNOWN_ERROR)) // Unknown error
    }
}.flowOn(Dispatchers.IO)
