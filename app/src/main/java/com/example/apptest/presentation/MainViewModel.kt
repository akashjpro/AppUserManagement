package com.example.apptest.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.apptest.domain.model.User
import com.example.apptest.domain.usecases.UsersUseCases
import com.example.apptest.presentation.navgraph.Route
import com.example.apptest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersUseCases: UsersUseCases
): ViewModel() {

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        _startDestination.value = Route.NewsNavigation.route
    }
}