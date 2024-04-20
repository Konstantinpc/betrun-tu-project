package com.example.betrun.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betrun.R
import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.ui.components.home.BetrunCalendar
import com.example.betrun.ui.viewmodels.home.HomeViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgramScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    when(val activeDaysResponse = homeViewModel.activeDaysResponse) {
        is ResponseDatabase.Loading -> {
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxSize(),
                visible = true,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize()
                )
            }
        }
        is ResponseDatabase.Success -> {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.size(28.dp))
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 23.dp),
                        text = stringResource(id = R.string.your_program),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(47.dp))
                    activeDaysResponse.data?.let {
                        BetrunCalendar(
                            modifier = Modifier
                                .padding(horizontal = 18.dp),
                            selections = it.map { string ->
                                CalendarDay(
                                    date = LocalDate.parse(string.take(10)),
                                    position = DayPosition.MonthDate
                                )
                            }
                        )
                    }
                }
            }
        }
        is ResponseDatabase.Failure -> print(activeDaysResponse.e)
    }
}