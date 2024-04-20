package com.example.betrun.ui.screens.questions.question

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun QuestionHolder(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    @StringRes descriptionResourceId: Int? = null,
    content: @Composable (() -> Unit)?,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(35.dp))
        QuestionTitle(titleResourceId)
        descriptionResourceId?.let {
            Spacer(modifier = Modifier.size(25.dp))
            QuestionDescription(it)
        }
        Spacer(modifier = Modifier.size(50.dp))
        if (content != null) {
            content()
        }
    }
}

@Composable
private fun QuestionTitle(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun QuestionDescription(
    @StringRes directionsResourceId: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = directionsResourceId),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}