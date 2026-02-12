@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.studentdispatch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        Column(
            modifier = Modifier.padding(pad).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
            ) {
                Text("ورود")
            }
        }
    }
}
