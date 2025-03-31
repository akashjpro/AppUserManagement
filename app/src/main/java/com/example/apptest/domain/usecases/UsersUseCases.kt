package com.example.apptest.domain.usecases

import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.UserRequest
import com.example.apptest.util.Resource
import kotlinx.coroutines.flow.Flow

interface UsersUseCases {
    suspend fun getUsers(): Flow<Resource<List<User>>>
    suspend fun getUser(id: Long): Flow<Resource<User>>
    suspend fun addUser(user: UserRequest): Flow<Resource<User>>
    suspend fun updateUser(user: User): Flow<Resource<User>>
    suspend fun deleteUser(id: Long): Flow<Resource<User>>
}