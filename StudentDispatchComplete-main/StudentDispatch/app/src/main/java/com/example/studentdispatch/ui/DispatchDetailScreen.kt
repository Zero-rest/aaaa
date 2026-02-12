@file:OptIn(androidx.compose.material.ExperimentalMaterialApi::class)

package com.example.studentdispatch.ui

import androidx.compose.material.ExperimentalMaterialApi

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun DispatchDetailScreen(
    instId: Int,
    state: DispatchUiState,
    onBack: () -> Unit
) {
    val ctx = LocalContext.current
    val inst = state.all.firstOrNull { it.id == instId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جزئیات موسسه") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("بازگشت") }
                }
            )
        }
    ) { pad ->
        if (inst == null) {
            Box(Modifier.padding(pad).padding(16.dp)) {
                Text("یافت نشد.")
            }
            return@Scaffold
        }

        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(inst.name, style = MaterialTheme.typography.headlineSmall)

            if (inst.isSponsored) {
                AssistChip(onClick = {}, label = { Text("تبلیغ | هزینه: ${inst.adCost}") })
            }

            Text("کشور: ${inst.country}")
            Text("شهر: ${inst.city}")
            Text("کارهای موفق: ${inst.successfulCases}")

            Divider()

            Text(inst.description)

            Divider()

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = {
                    val uri = Uri.parse("tel:${inst.phone}")
                    ctx.startActivity(Intent(Intent.ACTION_DIAL, uri))
                }) { Text("تماس") }

                OutlinedButton(onClick = {
                    val url = if (inst.website.startsWith("http")) inst.website else "https://${inst.website}"
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }) { Text("وبسایت") }
            }
        }
    }
}
