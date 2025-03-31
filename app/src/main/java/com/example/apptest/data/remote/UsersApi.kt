package com.example.apptest.data.remote

import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.UserRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi {

    @GET("user")
    suspend fun getUsers(
    ): List<User>

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id: Long
    ): User

    @POST("user")
    suspend fun addUser(
        @Body user: UserRequest
    ): User

    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: UserRequest
    ): User

    @DELETE("user/{id}")
    suspend fun deleteUser(
        @Path("id") id: Long
    ): User

}