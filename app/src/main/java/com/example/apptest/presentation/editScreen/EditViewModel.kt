package com.example.apptest.presentation.editScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.UserRequest
import com.example.apptest.domain.usecases.UsersUseCases
import com.example.apptest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val usersUseCases: UsersUseCases
): ViewModel() {
    private  val _uiState = MutableStateFlow<Resource<User>>(Resource.loading())
    val uiState: StateFlow<Resource<User>> = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _serverError = MutableStateFlow<String?>(null)
    val serverError: StateFlow<String?> = _serverError

    fun updateUser(user: User) {
        viewModelScope.launch {
            usersUseCases.updateUser(user).collect { resource ->
                _uiState.value = resource
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                    }

                    is Resource.Error -> {
                        _serverError.value = resource.code.name
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun resetUIState(){
        _uiState.value = Resource.loading()
    }
}