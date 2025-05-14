package com.example.atobe.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.atobe.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtoBeTopBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    currentScreenTitle: String,
    scrollBehavior: TopAppBarScrollBehavior?
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = currentScreenTitle
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(navigateUp) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}
