package com.example.betrun.ui.screens.home

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.betrun.R
import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.map.Run
import com.example.betrun.domain.model.questions.PersonData
import com.example.betrun.ui.components.home.DialogSetWeight
import com.example.betrun.ui.components.home.HistoryRunRow
import com.example.betrun.ui.components.home.RunInfoDialog
import com.example.betrun.ui.components.home.WeightLineChart
import com.example.betrun.ui.viewmodels.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    lifecycleScope: CoroutineScope,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val setWeightDialog = remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(uri)
            .target { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                viewModel.setProfilePic(data)
            }
            .build()
        loader.enqueue(request)
    }

    val runsResponse = viewModel.runsResponse
    val personDataResponse = viewModel.personDataResponse
    val profilePicResponse = viewModel.profilePicResponse
    val weightChartDataResponse = viewModel.weightChartDataResponse

    if(
        runsResponse is ResponseDatabase.Loading ||
        personDataResponse is ResponseDatabase.Loading ||
        profilePicResponse is ResponseDatabase.Loading ||
        weightChartDataResponse is ResponseDatabase.Loading
    ) {
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
    } else {
        ContentProfile(
            runsResponse = runsResponse,
            personDataResponse = personDataResponse,
            profilePicResponse = profilePicResponse,
            weightChartDataResponse = weightChartDataResponse,
            onSignOut = {
                lifecycleScope.launch {
                    viewModel.logout()
                    navHostController.navigateUp()
                }
            },
            openDialogRun = { viewModel.setDialogRun(it) },
            openSetWeightDialog = { setWeightDialog.value = true },
            openLauncher = { launcher.launch("image/*") },
            imageUri = imageUri
        )
    }

    viewModel.dialogRun.collectAsStateWithLifecycle().value?.let {
        RunInfoDialog(
            run = it,
            onDismiss = { viewModel.setDialogRun(null) }
        )
    }
    if (setWeightDialog.value) {
        DialogSetWeight(onDismiss = { setWeightDialog.value = false }) {
            viewModel.setCurrentWeight(it.text.toFloat())
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentProfile(
    runsResponse: ResponseDatabase<List<Run>>,
    personDataResponse: ResponseDatabase<PersonData>,
    profilePicResponse: ResponseDatabase<String>,
    weightChartDataResponse: ResponseDatabase<Map<LocalDate, Float>>,
    onSignOut: () -> Unit,
    openDialogRun: (Run) -> Unit,
    openSetWeightDialog: () -> Unit,
    openLauncher: () -> Unit,
    imageUri: Uri?
){
    LazyColumn {
        item {
            Spacer(modifier = Modifier.size(28.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 23.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.your_profile),
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        onSignOut()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.size(45.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 3.5.dp,
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePicResponse is ResponseDatabase.Success) {
                        if (profilePicResponse.data != null || imageUri != null) {
                            AsyncImage(
                                model = imageUri ?: profilePicResponse.data,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(86.dp)
                                    .padding(7.5.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        openLauncher()
                                    },
                                contentScale = ContentScale.Crop,
                            )
                        } else {
                            Canvas(
                                modifier = Modifier
                                    .size(86.dp)
                                    .padding(7.5.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        openLauncher()
                                    },
                                onDraw = {
                                    drawCircle(color = Color.Gray)
                                }
                            )
                        }
                    } else {
                        Canvas(
                            modifier = Modifier
                                .size(86.dp)
                                .padding(7.5.dp)
                                .clip(CircleShape)
                                .clickable {
                                    openLauncher()
                                },
                            onDraw = {
                                drawCircle(color = Color.Gray)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = if (personDataResponse is ResponseDatabase.Success) personDataResponse.data?.name.toString() else "",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = stringResource(id = R.string.athlete),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { openSetWeightDialog() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text =
                            if (weightChartDataResponse is ResponseDatabase.Success &&
                                weightChartDataResponse.data?.size!! > 0)
                                weightChartDataResponse.data.entries.maxByOrNull { it.key }!!.value.toString()
                            else
                                if (personDataResponse is ResponseDatabase.Success)
                                    personDataResponse.data?.currentWeight.toString()
                                else 0f.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Your weight",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                VerticalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (personDataResponse is ResponseDatabase.Success) personDataResponse.data?.weightGoal.toString() else 0f.toString(), //weightGoal.value.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Weight goal",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(37.dp))
            if (weightChartDataResponse is ResponseDatabase.Success) {
                weightChartDataResponse.data?.let {
                    WeightLineChart(
                        modifier = Modifier,
                        map = it
                    )
                }
            }
            Spacer(modifier = Modifier.height(33.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 23.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }
        if (runsResponse is ResponseDatabase.Success) {
            if (runsResponse.data?.size!! > 0) {
                item {
                    Spacer(modifier = Modifier.height(29.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 23.dp),
                        text = stringResource(id = R.string.history),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(29.dp))
                }
                items(runsResponse.data) {
                    HistoryRunRow(
                        run = it,
                        onClick = { openDialogRun(it) }
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(110.dp))
        }
    }
}
