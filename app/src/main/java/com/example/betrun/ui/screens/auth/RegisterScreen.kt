package com.example.betrun.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.betrun.R
import com.example.betrun.domain.model.auth.Response
import com.example.betrun.ui.components.auth.MyCircularProgress
import com.example.betrun.ui.components.common.BetrunButton
import com.example.betrun.ui.viewmodels.auth.RegisterViewModel
import com.example.betrun.util.navigation.Destination
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navHostController: NavHostController,
) {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val registerViewModel = koinViewModel<RegisterViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = hostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                title = {  },
                navigationIcon = {
                    IconButton(
                        onClick = { navHostController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_button),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            registerFlowState = registerViewModel.registerFlow,
            onNavigateToLogin = { navHostController.navigate(Destination.Login.route) },
            onRegister = { email, password -> registerViewModel.register(email, password) },
            registerSuccess = { navHostController.navigate(Destination.QuestionsProgress.route) },
            registerError = { scope.launch { hostState.showSnackbar("Oops! something went wrong, check your connection and try again") } }
        )
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    registerFlowState: MutableSharedFlow<Response<AuthResult>>,
    onRegister: (String, String) -> Unit,
    onNavigateToLogin: () -> Unit,
    registerSuccess: () -> Unit,
    registerError: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 25.dp)
    ) {
        val emailText = remember {
            mutableStateOf("")
        }
        val passwordText = remember {
            mutableStateOf("")
        }
        var showPassword by remember { mutableStateOf(value = false) }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(40.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = emailText.value,
            onValueChange = { text -> emailText.value = text },
            label = { Text(stringResource(id = R.string.email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Filled.Email, "email") },
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = passwordText.value,
            onValueChange = { text -> passwordText.value = text },
            label = { Text(stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.size(40.dp))
        BetrunButton(
            onClick = { onRegister(emailText.value, passwordText.value) },
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.register)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = buildAnnotatedString {
                append(stringResource(id = R.string.already_have_an_account))
                withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary)) { append(
                    stringResource(id = R.string._login)) }
            },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.CenterHorizontally)
                .padding(20.dp)
                .clickable { onNavigateToLogin() }
        )
    }
    RegisterState(
        registerFlowState = registerFlowState,
        onSuccess = { registerSuccess() },
        onError = { registerError() }
    )
}

@Composable
fun RegisterState(
    registerFlowState: MutableSharedFlow<Response<AuthResult>>,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    if (isLoading.value) MyCircularProgress()
    LaunchedEffect(Unit) {
        registerFlowState.collect {
            when (it) {
                is Response.Loading -> {
                    Log.i("Register state -> ", "Loading")
                    isLoading.value = true
                }

                is Response.Error -> {
                    Log.e("Register state -> ", it.message)
                    isLoading.value = false
                    onError()
                }

                is Response.Success -> {
                    Log.i("Register state -> ", "Success")
                    isLoading.value = false
                    onSuccess()
                }
            }
        }
    }
}