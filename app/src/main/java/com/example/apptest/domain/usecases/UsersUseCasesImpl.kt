package com.example.apptest.domain.usecases

import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.UserRequest
import com.example.apptest.domain.repository.UsersRepository
import com.example.apptest.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCasesImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : UsersUseCases {

    override suspend fun getUsers(): Flow<Resource<List<User>>> {
        return usersRepository.getUsers()
    }

    override suspend fun getUser(id: Long): Flow<Resource<User>> {
        return usersRepository.getUser(id)
    }

    override suspend fun addUser(user: UserRequest): Flow<Resource<User>> {
        return usersRepository.addUser(user)
    }

    override suspend fun updateUser(user: User): Flow<Resource<User>> {
        return usersRepository.updateUser(user)
    }

    override suspend fun deleteUser(id: Long): Flow<Resource<User>> {
        return usersRepository.deleteUser(id)
    }
}
