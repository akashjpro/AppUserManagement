package com.example.apptest.presentation.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SavedScreen(
    viewModel: SavedViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .statusBarsPadding()
    ) {

        Row {
            Spacer(Modifier.width(16.dp))
            Text("Saved User Screen", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(Modifier.height(8.dp))

    }
}
