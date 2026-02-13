@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.studentdispatch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentdispatch.data.Institution

@Composable
fun AdminDashboardScreen(
    state: DispatchUiState,
    onLogout: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Institution) -> Unit,
    onReseed: () -> Unit,
    onBack: () -> Unit
) {
    if (!state.isAdmin) {
        onBack()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("پنل ادمین") },
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } },
                actions = { TextButton(onClick = onLogout) { Text("خروج") } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Text("+") }
        }
    ) { pad ->
        Column(
            modifier = Modifier.padding(pad).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = onReseed) { Text("ریست + نمونه") }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.all) { inst ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(inst.name, style = MaterialTheme.typography.titleMedium)
                            Text("Sponsored: ${inst.isSponsored} | adCost: ${inst.adCost} | موفق: ${inst.successfulCases}")

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(onClick = { onEdit(inst.id) }) { Text("ویرایش") }
                                OutlinedButton(onClick = { onDelete(inst) }) { Text("حذف") }
                            }
                        }
                    }
                }
            }
        }
    }
}
