package com.example.apptest.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apptest.domain.model.User
import com.example.apptest.util.Resource
import com.example.apptest.util.convertTimestampToDate

@Composable
fun DetailsScreen(
    viewModel: DetailViewModel,
    userInitial: User,
    navigateUp: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    var user by remember { mutableStateOf(userInitial) }

    LaunchedEffect(Unit) {
        viewModel.getUser(userInitial.id)
    }

    when (uiState) {
        is Resource.Loading -> {

        }

        is Resource.Success -> {
            user = (uiState as Resource.Success<User>).data
        }

        is Resource.Error -> {

        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        DetailsTopBar(
            title = "Detail Screen",
            onBackClick = navigateUp
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {

            Row {

                Text(
                    text = "First Name: ",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = user.firstName,
                    style = MaterialTheme.typography.titleMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = "Last Name: ",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = user.lastName,
                    style = MaterialTheme.typography.titleMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = "Age: ",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = user.age.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = "Gender: ",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = if (user.male) "Male" else "Female",
                    style = MaterialTheme.typography.titleMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = "Address: ",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = user.address,
                    style = MaterialTheme.typography.titleMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = "Birthday: ",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = convertTimestampToDate(user.birthday),
                    style = MaterialTheme.typography.titleMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}