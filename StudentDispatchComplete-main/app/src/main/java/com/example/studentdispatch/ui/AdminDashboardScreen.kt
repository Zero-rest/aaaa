@file:OptIn(androidx.compose.material.ExperimentalMaterialApi::class)

@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.studentdispatch.ui

import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentdispatch.data.Institution

@OptIn(ExperimentalMaterial3Api::class)
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
        // اگر کسی مستقیم آدرس رو زد
        onBack()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("پنل ادمین") },
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } },
                actions = {
                    TextButton(onClick = onLogout) { Text("خروج") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Text("+") }
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = onReseed) { Text("ریست + نمونه") }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.all) { inst ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
