@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.studentdispatch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
        onBack()
        return
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
        Column(
            modifier = Modifier.padding(pad).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it; err = "" },
                label = { Text("نام موسسه") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it; err = "" },
                label = { Text("کشور") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it; err = "" },
                label = { Text("شهر") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it; err = "" },
                label = { Text("تلفن") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = website,
                onValueChange = { website = it; err = "" },
                label = { Text("وبسایت") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it; err = "" },
                label = { Text("توضیحات") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = success,
                onValueChange = { success = it.filter { ch -> ch.isDigit() }; err = "" },
                label = { Text("تعداد کارهای موفق") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = sponsored, onCheckedChange = { sponsored = it })
                Spacer(Modifier.width(8.dp))
                Text("نمایش به عنوان تبلیغ (Sponsored)")
            }

            OutlinedTextField(
                value = adCost,
                onValueChange = { adCost = it.filter { ch -> ch.isDigit() }; err = "" },
                label = { Text("هزینه تبلیغ (adCost)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = sponsored
            )

            if (err.isNotBlank()) {
                Text(err, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    val s = success.toIntOrNull()
                    val a = adCost.toIntOrNull()

                    if (name.isBlank()) { err = "نام موسسه الزامی است."; return@Button }
                    if (s == null) { err = "تعداد کارهای موفق معتبر نیست."; return@Button }
                    if (sponsored && a == null) { err = "هزینه تبلیغ معتبر نیست."; return@Button }

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
