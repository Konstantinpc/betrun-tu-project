package com.example.betrun.ui.screens.questions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betrun.R
import com.example.betrun.ui.components.questions.NumberListStartCircle
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit

@Preview(showBackground = true)
@Composable
fun QuestionsFinalScreen(
    onDonePressed: () -> Unit = {},
) {
    Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
        Scaffold(
            content = { innerPadding ->
                val modifier = Modifier.padding(innerPadding)
                QuestionsFinalScreenContent(
                    modifier = modifier
                )
            },
            bottomBar = {
                QuestionsFinalScreenBottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
                        .padding(start = 25.dp, end = 25.dp, bottom = 45.dp, top = 25.dp),
                    onDonePressed = onDonePressed
                )
            }
        )
    }
}

@Composable
private fun QuestionsFinalScreenBottomBar(
    modifier: Modifier = Modifier,
    onDonePressed: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = onDonePressed,
            shape = RoundedCornerShape(7.dp),
        ) {
            Text(
                text = stringResource(id = R.string.let_s_do_it),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Composable
private fun QuestionsFinalScreenContent(
    modifier: Modifier = Modifier
) {
    val state = listOf(
        Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            shapes = listOf(Shape.Square),
            fadeOutEnabled = true,
            size = listOf(Size.MEDIUM),
            colors = listOf(0x44CAAC),
            position = Position.Relative(0.5, -0.2),
            emitter = Emitter(duration = 2000, TimeUnit.MILLISECONDS).max(2000)
        )
    )
    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = state,
    )
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)) {
        item {
            Spacer(modifier = Modifier.height(45.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.tick), contentDescription = null)
            Spacer(modifier = Modifier.size(50.dp))
            Text(
                text = stringResource(id = R.string.betrun_recap_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.size(26.dp))
            Text(
                text = stringResource(id = R.string.betrun_recap_description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(50.dp))
            Row {
                NumberListStartCircle(num = "1")
                Spacer(modifier = Modifier.size(17.dp))
                Text(
                    text = stringResource(id = R.string.betrun_recap_1),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.size(36.dp))
            Row {
                NumberListStartCircle(num = "2")
                Spacer(modifier = Modifier.size(17.dp))
                Text(
                    text = stringResource(id = R.string.betrun_recap_2),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.size(36.dp))
            Row {
                NumberListStartCircle(num = "3")
                Spacer(modifier = Modifier.size(17.dp))
                Text(
                    text = stringResource(id = R.string.betrun_recap_3),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.size(36.dp))
            Row {
                NumberListStartCircle(num = "4")
                Spacer(modifier = Modifier.size(17.dp))
                Text(
                    text = stringResource(id = R.string.betrun_recap_4),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
            }
        }
    }
}