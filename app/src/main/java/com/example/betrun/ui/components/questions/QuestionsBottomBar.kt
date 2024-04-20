package com.example.betrun.ui.components.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betrun.R

@Composable
fun QuestionsBottomBar(
    shouldShowSecondaryButton: Boolean,
    shouldShowContinueButton: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onContinuePressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
            .padding(start = 25.dp, end = 25.dp, bottom = 45.dp, top = 25.dp)

    ) {
        if (shouldShowSecondaryButton) {
            OutlinedButton(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = onPreviousPressed,
                shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.previous),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
        }
        if (shouldShowContinueButton) {
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = onContinuePressed,
                enabled = isNextButtonEnabled,
                shape = RoundedCornerShape(7.dp),
            ) {
                Text(text = stringResource(id = R.string.user_continue),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = onNextPressed,
                enabled = isNextButtonEnabled,
                shape = RoundedCornerShape(7.dp),
            ) {
                Text(text = stringResource(id = R.string.user_continue),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
