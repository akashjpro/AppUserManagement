package com.example.apptest.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptest.domain.model.User
import com.example.apptest.presentation.addScreen.GenderSelection
import com.example.apptest.util.convertMillisToDate
import com.example.apptest.util.convertTimestampToDate
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserFormScreen(
    user: User? = null,
    onSubmit: (User) -> Unit
) {
    var firstName by remember { mutableStateOf(user?.firstName ?: "") }
    var lastName by remember { mutableStateOf(user?.lastName ?: "") }
    var age by remember { mutableStateOf(user?.age?.toString() ?: "") }
    var address by remember { mutableStateOf(user?.address ?: "") }
    var selectedGender by remember { mutableStateOf(if (user?.male == true) "Male" else "Female") }
    var selectedDate by remember { mutableStateOf(user?.birthday) }

    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var ageError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var genderError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it; firstNameError = false },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = firstNameError,
            supportingText = { if (firstNameError) Text("First name cannot be empty") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it; lastNameError = false },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = lastNameError,
            supportingText = { if (lastNameError) Text("Last name cannot be empty") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it; ageError = false },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            isError = ageError,
            supportingText = { if (ageError) Text("Age must be a number") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it; addressError = false },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = addressError,
            supportingText = { if (addressError) Text("Address cannot be empty") }
        )

        Spacer(Modifier.height(8.dp))

        DatePickerFieldToModal(
            selectedDate = selectedDate,
            onDateSelected = { newDate ->
                selectedDate = newDate
                dateError = false
            }
        )
        if (dateError) {
            Text("Date of birth cannot be empty", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        GenderSelection(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it; genderError = false }
        )
        if (genderError) {
            Text("Gender cannot be empty", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                firstNameError = firstName.isBlank()
                lastNameError = lastName.isBlank()
                ageError = age.toIntOrNull() == null
                addressError = address.isBlank()
                dateError = selectedDate == null
                genderError = selectedGender.isBlank()

               val  birthday = (selectedDate ?: Instant.now().epochSecond) / 1000

                if (!firstNameError && !lastNameError && !ageError && !addressError && !dateError && !genderError) {
                    val userObj = User(
                        id = user?.id ?: 0,
                        firstName = firstName,
                        lastName = lastName,
                        age = age.toInt(),
                        address = address,
                        birthday = birthday,
                        male = selectedGender == "Male"
                    )
                    onSubmit(userObj)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (user == null) "Add User" else "Update User")
        }
    }
}

@Composable
fun DatePickerFieldToModal(
    modifier: Modifier = Modifier,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit
) {
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let {convertTimestampToDate(it) } ?: "",
        onValueChange = { },
        label = { Text("DOB") },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { date ->
                onDateSelected(date)
                showModal = false
            },
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


