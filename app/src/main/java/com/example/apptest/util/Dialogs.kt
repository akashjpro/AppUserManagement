package com.example.apptest.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog() {
    AlertDialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Loading...")
            }
        },
        text = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Please wait...")
                }
            }
        },
        confirmButton = { }
    )

}

//@Composable
//fun ErrorDialog(message: String, isShowDialog: Boolean = false, onDismiss: () -> Unit) {
//    var showDialog by remember { mutableStateOf(isShowDialog) }
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false; onDismiss() },
//            title = { Text("Error") },
//            text = { Text(message) },
//            confirmButton = {
//                Button(onClick = { showDialog = false; onDismiss() }) {
//                    Text("OK")
//                }
//            }
//        )
//    }
//}


@Composable
fun AlertInfoDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("OK")
            }
        },
//        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text("Dismiss")
//            }
//        }
    )
}

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("OK")
            }
        },
//        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text("Dismiss")
//            }
//        }
    )
}

