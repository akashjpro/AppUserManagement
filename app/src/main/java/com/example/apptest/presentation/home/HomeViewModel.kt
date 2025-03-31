package com.example.apptest.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptest.domain.model.User
import com.example.apptest.domain.usecases.UsersUseCases
import com.example.apptest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersUseCases: UsersUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<User>>>(Resource.loading())
    val uiState: StateFlow<Resource<List<User>>> = _uiState

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            usersUseCases.getUsers().collect{ resource ->
                _uiState.value = resource
            }
        }
    }

    fun deleteUser(id: Long) {
        viewModelScope.launch {
            usersUseCases.deleteUser(id).collect { result ->
                if (result is Resource.Success) {
                    getUsers()
                }
            }
        }
    }
}