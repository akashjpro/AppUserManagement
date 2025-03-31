package com.example.apptest.presentation.home.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apptest.domain.model.User
import com.example.apptest.presentation.Dimens.ExtraSmallPadding2
import com.example.apptest.presentation.Dimens.MediumPadding1
import com.example.apptest.presentation.common.ArticleCardShimmerEffect
import com.example.apptest.presentation.common.EmptyScreen
import com.example.apptest.util.Resource

@Composable
fun UsersList(
    modifier: Modifier = Modifier,
    resource: Resource<List<User>>,
    onClick: (User) -> Unit,
    updateUser: (User) -> Unit,
    deleteUser: (User) -> Unit,
    onRetry: () -> Unit
) {

    when (resource) {
        is Resource.Loading -> {
            ShimmerEffect()
        }

        is Resource.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                contentPadding = PaddingValues(all = ExtraSmallPadding2)
            ) {

                items(
                    items = resource.data,
                    key = { it.id }
                ) { user ->
                    UserCard(
                        user = user,
                        onClick = {
                            onClick(user)
                        },
                        updateUser = {
                            updateUser(user)
                        },
                        deleteUser = {
                            deleteUser(user)
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.3.dp)
                            .background(Color.Gray)
                    )
                }
            }
        }

        is Resource.Error -> {
            EmptyScreen(
                errorString = resource.code.name,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}