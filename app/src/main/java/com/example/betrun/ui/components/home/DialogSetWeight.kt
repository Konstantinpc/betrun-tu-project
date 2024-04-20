package com.example.betrun.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.betrun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSetWeight(
    onDismiss: () -> Unit,
    onValueChanged: (TextFieldValue) -> Unit
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.set_weight),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                label = { Text(text = stringResource(R.string.weight)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                suffix = { Text(text = stringResource(id = R.string.current_weight_question_helper)) }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                enabled = text.text.isNotBlank(),
                onClick = { onValueChanged(text); onDismiss()}
            ) {
                Text(text = stringResource(R.string.set))
            }
        }
    }
}