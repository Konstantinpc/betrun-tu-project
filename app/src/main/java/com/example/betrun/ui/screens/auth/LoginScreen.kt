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
import androidx.compose.material.icons.filled.Lock
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
import androidx.navigation.compose.rememberNavController
import com.example.betrun.R
import com.example.betrun.domain.model.auth.Response
import com.example.betrun.ui.components.auth.MyAlertDialog
import com.example.betrun.ui.components.auth.MyCircularProgress
import com.example.betrun.ui.components.common.BetrunButton
import com.example.betrun.ui.viewmodels.auth.LoginViewModel
import com.example.betrun.util.navigation.Destination
import com.example.betrun.util.navigation.Graphs
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHostController: NavHostController = rememberNavController(),

) {
    val loginViewModel = koinViewModel<LoginViewModel>()
    val hostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = hostState) },
        modifier = Modifier.fillMaxSize(),
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
            hostState = hostState,
            paddingValues = paddingValues,
            signInStateFlow = loginViewModel.loginFlow,
            resetPasswordStateFlow = loginViewModel.resetPasswordFlow,
            onRegisterNow = { navHostController.navigate(Destination.Register.route) },
            onForgotPassword = { email -> loginViewModel.resetPassword(email) },
            onLogin = { email, password -> loginViewModel.login(email, password) },
            loginSuccess = { navHostController.navigate(Graphs.MAIN) { popUpTo(0) } }
        )
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    signInStateFlow: MutableSharedFlow<Response<AuthResult>>,
    resetPasswordStateFlow: MutableSharedFlow<Response<Void?>>,
    onRegisterNow: () -> Unit,
    onForgotPassword: (String) -> Unit,
    onLogin: (String, String) -> Unit,
    loginSuccess: () -> Unit,
    hostState: SnackbarHostState
) {
    val emailText = remember {
        mutableStateOf("")
    }
    val passwordText = remember {
        mutableStateOf("")
    }
    var showForgotPasswordDialog by remember {
        mutableStateOf(false)
    }

    var showPassword by remember { mutableStateOf(value = false) }

    val scope = rememberCoroutineScope()

    if (showForgotPasswordDialog)
        MyAlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            onConfirmation = {
                if (emailText.value != "") {
                    onForgotPassword(emailText.value)
                    showForgotPasswordDialog = false
                } else {
                    scope.launch {
                        hostState.showSnackbar("Please enter email address")
                    }
                }
            },
            title = stringResource(id = R.string.forgot_password),
            text = stringResource(id = R.string.dialog_forgot_password_text),
            confirmButtonText = stringResource(id = R.string.send),
            dismissButtonText = stringResource(id = R.string.cancel),
            cancelable = true
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 25.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(40.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            singleLine = true,
            value = emailText.value,
            onValueChange = { text -> emailText.value = text },
            label = { Text(stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Filled.Email, "email") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            value = passwordText.value,
            onValueChange = { text -> passwordText.value = text },
            label = { Text(stringResource(id = R.string.password)) },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            },
            leadingIcon = { Icon(Icons.Filled.Lock, "password") }
        )
        Spacer(modifier = Modifier.size(40.dp))
        BetrunButton(
            onClick = { onLogin(emailText.value, passwordText.value) },
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.login)
        )
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(id = R.string.forgot_password),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.CenterHorizontally)
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .clickable { showForgotPasswordDialog = true },
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.don_t_have_an_account_))
                withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary)) { append(stringResource(id = R.string._register_now)) }
            },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.CenterHorizontally)
                .padding(20.dp)
                .clickable { onRegisterNow() },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
    LoginInState(
        flow = signInStateFlow,
        onSuccess = { loginSuccess() },
        onError = { scope.launch { hostState.showSnackbar("The email address or password is incorrect") } }
    )
    ResetPasswordState(
        flow = resetPasswordStateFlow,
        onSuccess = { scope.launch { hostState.showSnackbar("Email sent successfully, check your inbox") } },
        onError = { scope.launch { hostState.showSnackbar("Oops! something went wrong, try again") } }
    )
}

@Composable
fun LoginInState(
    flow: MutableSharedFlow<Response<AuthResult>>,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    if (isLoading.value) MyCircularProgress()
    LaunchedEffect(Unit) {
        flow.collect {
            when (it) {
                is Response.Loading -> {
                    Log.i("Login state -> ", "Loading")
                    isLoading.value = true
                }

                is Response.Error -> {
                    Log.e("Login state -> ", it.message)
                    isLoading.value = false
                    onError()
                }

                is Response.Success -> {
                    Log.i("Login state -> ", "Success")
                    isLoading.value = false
                    onSuccess()
                }
            }
        }
    }
}

@Composable
fun ResetPasswordState(
    flow: MutableSharedFlow<Response<Void?>>,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    if (isLoading.value) MyCircularProgress()
    LaunchedEffect(Unit) {
        flow.collect {
            when (it) {
                is Response.Loading -> {
                    Log.i("Reset password state", "Loading")
                    isLoading.value = true
                }

                is Response.Error -> {
                    Log.e("Reset password state", it.message)
                    isLoading.value = false
                    onError()
                }

                is Response.Success -> {
                    Log.i("Reset password state", "Success")
                    isLoading.value = false
                    onSuccess()
                }
            }
        }
    }
}