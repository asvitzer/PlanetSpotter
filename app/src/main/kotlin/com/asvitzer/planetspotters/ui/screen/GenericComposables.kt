package com.asvitzer.planetspotters.ui.screen

import androidx.compose.material.Text

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = modifier
            .padding(8.dp)
            .height(48.dp)

    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp),
                color = MaterialTheme.colors.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(text)
        }
    }
}

@Composable
@Preview
fun LoadingButtonPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingButton(
            text = "Submit",
            isLoading = false,
            onClick = {
            }
        )
    }
}