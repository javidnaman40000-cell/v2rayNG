package com.v2ray.ang.ui.compose

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun <T> AppDropdownMenuItems(
    items: List<T>,
    labelRes: (T) -> Int,
    onSelected: (T) -> Unit,
    textColor: Color = Color.Unspecified
) {
    items.forEach { item ->
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(labelRes(item)),
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )
            },
            onClick = { onSelected(item) }
        )
    }
}
