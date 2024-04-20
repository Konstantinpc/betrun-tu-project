package com.example.betrun.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.betrun.domain.model.map.Run

@Composable
fun HistoryRunRow(
    run: Run,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 5.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = run.img,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(10.dp))
        run.date?.let {
            Text(
                modifier = Modifier.weight(1f),
                text = it
            )
        }
    }
}
