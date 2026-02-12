@file:OptIn(androidx.compose.material.ExperimentalMaterialApi::class)

package com.example.studentdispatch.ui

import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentdispatch.data.Institution

@Composable
fun AdminInstitutionFormScreen(
    state: DispatchUiState,
    editId: Int, // -1 یعنی افزودن
    onSave: (Institution) -> Unit,
    onBack: () -> Unit
) {
    if (!state.isAdmin) {
        onBack(); return
    }

    val editing = state.all.firstOrNull { it.id == editId }

    var name by remember { mutableStateOf(editing?.name ?: "") }
    var country by remember { mutableStateOf(editing?.country ?: "") }
    var city by remember { mutableStateOf(editing?.city ?: "") }
    var phone by remember { mutableStateOf(editing?.phone ?: "") }
    var website by remember { mutableStateOf(editing?.website ?: "") }
    var desc by remember { mutableStateOf(editing?.description ?: "") }
    var success by remember { mutableStateOf((editing?.successfulCases ?: 0).toString()) }
    var sponsored by remember { mutableStateOf(editing?.isSponsored ?: false) }
    var adCost by remember { mutableStateOf((editing?.adCost ?: 0).toString()) }
    var err by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editing == null) "افزودن موسسه" else "ویرایش موسسه") },
                navigationIcon = { TextButton(onClick = onBack) { Text("بازگشت") } }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            OutlinedTextField(name, { name = it; err="" }, label = { Text("نام موسسه") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(country, { country = it; err="" }, label = { Text("کشور") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(city, { city = it; err="" }, label = { Text("شهر") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(phone, { phone = it; err="" }, label = { Text("تلفن") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(website, { website = it; err="" }, label = { Text("وبسایت") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(desc, { desc = it; err="" }, label = { Text("توضیحات") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

            OutlinedTextField(
                success, { success = it.filter { ch -> ch.isDigit() }; err="" },
                label = { Text("تعداد کارهای موفق") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Switch(checked = sponsored, onCheckedChange = { sponsored = it })
                Spacer(Modifier.width(8.dp))
                Text("نمایش به عنوان تبلیغ (Sponsored)")
            }

            OutlinedTextField(
                adCost,
                { adCost = it.filter { ch -> ch.isDigit() }; err="" },
                label = { Text("هزینه تبلیغ (adCost)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = sponsored
            )

            if (err.isNotBlank()) Text(err, color = MaterialTheme.colorScheme.error)

            Button(
                onClick = {
                    val s = success.toIntOrNull()
                    val a = adCost.toIntOrNull()
                    if (name.isBlank()) { err = "نام موسسه الزامی است."; return@Button }
                    if (s == null) { err = "تعداد کارهای موفق معتبر نیست."; return@Button }
                    if (sponsored && (a == null)) { err = "هزینه تبلیغ معتبر نیست."; return@Button }

                    onSave(
                        Institution(
                            id = editing?.id ?: 0,
                            name = name,
                            country = country,
                            city = city,
                            phone = phone,
                            website = website,
                            description = desc,
                            successfulCases = s,
                            isSponsored = sponsored,
                            adCost = if (sponsored) (a ?: 0) else 0
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("ذخیره") }
        }
    }
}
