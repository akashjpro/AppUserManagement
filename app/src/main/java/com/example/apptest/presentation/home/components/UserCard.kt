package com.example.apptest.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apptest.domain.model.User
import com.example.apptest.presentation.Dimens.ExtraSmallPadding
import com.example.apptest.ui.theme.AppTestTheme
import com.example.apptest.util.convertTimestampToDate

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    user: User,
    onClick: (() -> Unit)? = null,
    deleteUser: (() -> Unit)? = null,
    updateUser: (() -> Unit)? = null,
) {

    val context = LocalContext.current
    Row(
        modifier = modifier.clickable { onClick?.invoke() },

        ) {
        Spacer(Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically ) {
            Box(Modifier
                .width(80.dp)
                .height(IntrinsicSize.Max) // Để Box cao
                .clip(MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
                ) {
                Text(
                    text = user.id.toString(),
                    style = MaterialTheme.typography.headlineMedium.copy(),
                    color = Color.Black,
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(horizontal = ExtraSmallPadding).weight(1f)
            ) {
                Text(
                    text = user.firstName,
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.lastName,
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.age.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (user.male) "Male" else "Female",
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.address,
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = convertTimestampToDate(user.birthday),
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column {
                Button(onClick = {
                    updateUser?.invoke()
                }) {
                    Text("Update")
                }

                Spacer(Modifier.height(10.dp))

                Button(onClick = {
                    deleteUser?.invoke()
                }) {
                    Text("Delete")
                }
            }


        }
        Spacer(Modifier.height(2.dp))

    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    AppTestTheme(dynamicColor = false) {
        UserCard(
            user = User(
                id = 1,
                firstName = "firstName",
                lastName = "lastName",
                age = 19,
                male = true,
                address = "address",
                birthday = 1743161130
            )
        )
    }
}