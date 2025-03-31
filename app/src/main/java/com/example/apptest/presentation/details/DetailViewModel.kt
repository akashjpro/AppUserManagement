package com.example.apptest.presentation.details

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
class DetailViewModel @Inject constructor(
    private val usersUseCases: UsersUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow<Resource<User>>(Resource.loading())
    val uiState: StateFlow<Resource<User>> = _uiState


    fun getUser(id: Long) {
        viewModelScope.launch {
            usersUseCases.getUser(id).collect { resource ->
                _uiState.value = resource
            }
        }
    }

}