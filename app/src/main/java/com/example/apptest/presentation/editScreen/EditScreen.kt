package com.example.apptest.presentation.editScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apptest.domain.model.User
import com.example.apptest.presentation.common.UserFormScreen
import com.example.apptest.presentation.details.DetailsTopBar
import com.example.apptest.util.AlertInfoDialog
import com.example.apptest.util.ErrorDialog
import com.example.apptest.util.LoadingDialog
import com.example.apptest.util.Resource

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScreen(
    user: User,
    navigateUp: () -> Unit,
    viewModel: EditViewModel,
    navigateToHome: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val scrollState = rememberScrollState()

    val openAlertDialog = remember { mutableStateOf(false) }

    val openErrorDialog = remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    when {
        openAlertDialog.value -> {
            AlertInfoDialog(
                onDismissRequest = {
                },
                onConfirmation = {
                    openAlertDialog.value = false
                    viewModel.resetUIState()
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Success",
                dialogText = "User Edited successfully!",
            )
        }
    }

    when {
        openErrorDialog.value -> {
            ErrorDialog(
                onDismissRequest = {
                },
                onConfirmation = {
                    openErrorDialog.value = false
                    viewModel.resetUIState()
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Error",
                dialogText = errorMessage,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            title = "Edit User Screen",
            onBackClick = navigateUp
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {

            UserFormScreen(
                user = user,
                onSubmit = { user ->
                    viewModel.updateUser(user)
                }
            )

        }

        if (isLoading) {
            LoadingDialog()
        }

        when (uiState) {
            is Resource.Loading -> {
            }

            is Resource.Success -> {
                openAlertDialog.value = true
            }

            is Resource.Error -> {
                errorMessage = (uiState as Resource.Error<User>).code.name
                openErrorDialog.value = true
            }
        }
    }
}
