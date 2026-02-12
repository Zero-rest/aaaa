@file:OptIn(androidx.compose.material.ExperimentalMaterialApi::class)

@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.studentdispatch.ui

import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentdispatch.data.Institution

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatchListScreen(
    state: DispatchUiState,
    onQuery: (String) -> Unit,
    onCountry: (String) -> Unit,
    onCity: (String) -> Unit,
    onOpenDetail: (Int) -> Unit,
    onOpenAdmin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("اعزام دانشجو") },
                actions = {
                    TextButton(onClick = onOpenAdmin) { Text("ادمین") }
                }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(12.dp)) {

            OutlinedTextField(
                value = state.query,
                onValueChange = onQuery,
                label = { Text("جستجو (نام/توضیحات)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DropdownField(
                    label = "کشور",
                    value = state.country,
                    items = listOf("") + state.countries,
                    onSelect = { onCountry(it) },
                    modifier = Modifier.weight(1f)
                )
                DropdownField(
                    label = "شهر",
                    value = state.city,
                    items = listOf("") + state.cities,
                    onSelect = { onCity(it) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "نتایج: ${state.filtered.size}",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.filtered) { inst ->
                    InstitutionCard(inst = inst, onClick = { onOpenDetail(inst.id) })
                }
            }
        }
    }
}

@Composable
private fun InstitutionCard(inst: Institution, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(inst.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                if (inst.isSponsored) {
                    AssistChip(onClick = {}, label = { Text("تبلیغ") })
                }
            }
            Spacer(Modifier.height(6.dp))
            Text("${inst.country} - ${inst.city}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            Text("کارهای موفق: ${inst.successfulCases}", style = MaterialTheme.typography.bodySmall)
            if (inst.isSponsored) {
                Text("هزینه تبلیغ: ${inst.adCost}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownField(
    label: String,
    value: String,
    items: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(if (item.isBlank()) "همه" else item) },
                    onClick = {
                        onSelect(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
