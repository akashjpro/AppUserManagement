package com.example.apptest.presentation.addScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.toUserRequest
import com.example.apptest.presentation.common.UserFormScreen
import com.example.apptest.util.AlertInfoDialog
import com.example.apptest.util.ErrorDialog
import com.example.apptest.util.LoadingDialog
import com.example.apptest.util.Resource


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddScreen(
    viewModel: AddViewModel,
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
                dialogText = "User Add successfully!",
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
            .padding(top = 8.dp)
            .statusBarsPadding()
            .verticalScroll(scrollState)
    ) {

        Row {
            Spacer(Modifier.width(16.dp))
            Text("Add User Screen", style = MaterialTheme.typography.headlineMedium)
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {

            UserFormScreen(
                onSubmit = { user ->
                    viewModel.addUser(user.toUserRequest())
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

@Composable
fun GenderSelection(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Column {
        Text("Select Gender:")

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedGender == "Male",
                onClick = { onGenderSelected("Male") }
            )
            Text(
                "Male",
                modifier = Modifier.clickable { onGenderSelected("Male") }
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedGender == "Female",
                onClick = { onGenderSelected("Female") }
            )
            Text(
                "Female",
                modifier = Modifier.clickable { onGenderSelected("Female") }
            )
        }
    }
}



