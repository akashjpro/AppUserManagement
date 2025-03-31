package com.example.apptest.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.apptest.R
import com.example.apptest.domain.model.User
import com.example.apptest.presentation.Dimens.MediumPadding1
import com.example.apptest.presentation.home.components.UsersList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDetails: (User) -> Unit,
    navigateToEdit: (User) -> Unit,
    isRefresh: Boolean = false,
) {

    val uiState by viewModel.uiState.collectAsState()

    val isRefreshing by remember { mutableStateOf(false) }

    if (isRefresh) {
        viewModel.getUsers()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            viewModel.getUsers()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .statusBarsPadding()
        ) {

            Row {
                Spacer(Modifier.width(16.dp))
                Text("Home", style = MaterialTheme.typography.headlineMedium)
            }

            Spacer(Modifier.height(16.dp))

            UsersList(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                resource = uiState,
                onClick = navigateToDetails,
                updateUser = { user ->
                    navigateToEdit(user)
                },
                deleteUser = { user ->
                    viewModel.deleteUser(user.id)
                },
                onRetry = {
                    viewModel.getUsers()
                }
            )
        }
    }
}
