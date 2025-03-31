package com.example.apptest.domain.repository

import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.UserRequest
import kotlinx.coroutines.flow.Flow
import com.example.apptest.util.Resource

interface UsersRepository {

    suspend fun getUsers(): Flow<Resource<List<User>>>

    suspend fun getUser(id: Long): Flow<Resource<User>>

    suspend fun addUser(user: UserRequest): Flow<Resource<User>>

    suspend fun updateUser(user: User): Flow<Resource<User>>

    suspend fun deleteUser(id: Long): Flow<Resource<User>>

}