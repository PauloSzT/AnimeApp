package com.example.anime.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterButton (
    filterName: String,
    isSelected: Boolean,
    onButtonClick: () -> Unit
){
    Surface(
        modifier = Modifier.padding(4.dp)
    ){
        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor =
                if (isSelected) {
                    MaterialTheme.colorScheme.inversePrimary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            ),
            shape = ShapeDefaults.Medium
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = filterName.replace("_", " "),
                maxLines = 2,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
            )
        }
    }
}
