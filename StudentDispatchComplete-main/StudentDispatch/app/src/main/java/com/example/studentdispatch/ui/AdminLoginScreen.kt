@file:OptIn(androidx.compose.material.ExperimentalMaterialApi::class)

package com.example.studentdispatch.ui

import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminLoginScreen(
    onLogin: (String) -> Boolean,
    onBack: () -> Unit,
    onGoDashboard: () -> Unit
) {
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ورود ادمین") },
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it; error = "" },
                label = { Text("رمز ادمین") },
                modifier = Modifier.fillMaxWidth()
            )

            if (error.isNotBlank()) {
                Text(error, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    val ok = onLogin(pass)
                    if (ok) onGoDashboard() else error = "رمز اشتباه است."
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("ورود") }
        }
    }
}
