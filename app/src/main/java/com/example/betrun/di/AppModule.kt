package com.example.betrun.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.betrun.data.repository.AuthRepositoryImpl
import com.example.betrun.data.repository.ProfileRepositoryImpl
import com.example.betrun.data.repository.ProgramRepositoryImpl
import com.example.betrun.data.repository.QuestionsRepositoryImpl
import com.example.betrun.data.repository.WorkoutRepositoryImpl
import com.example.betrun.domain.repository.AuthRepository
import com.example.betrun.domain.repository.ProfileRepository
import com.example.betrun.domain.repository.ProgramRepository
import com.example.betrun.domain.repository.QuestionsRepository
import com.example.betrun.domain.repository.WorkoutRepository
import com.example.betrun.domain.usecase.auth.GetUserUidUseCase
import com.example.betrun.domain.usecase.auth.IsLoggedInUseCase
import com.example.betrun.domain.usecase.auth.LoginUseCase
import com.example.betrun.domain.usecase.auth.LogoutUseCase
import com.example.betrun.domain.usecase.auth.RegisterUseCase
import com.example.betrun.domain.usecase.auth.ResetPasswordUseCase
import com.example.betrun.domain.usecase.home.GetActiveDaysUseCase
import com.example.betrun.domain.usecase.home.GetPersonDataUseCase
import com.example.betrun.domain.usecase.home.GetProfilePicUseCase
import com.example.betrun.domain.usecase.home.GetRunsUseCase
import com.example.betrun.domain.usecase.home.GetWeightChartDataUseCase
import com.example.betrun.domain.usecase.home.SetCurrentWeightUseCase
import com.example.betrun.domain.usecase.home.SetNewRunUseCase
import com.example.betrun.domain.usecase.home.SetPersonDataUseCase
import com.example.betrun.domain.usecase.home.SetProfilePicUseCase
import com.example.betrun.ui.viewmodels.auth.AuthenticationNavigationViewModel
import com.example.betrun.ui.viewmodels.auth.LoginViewModel
import com.example.betrun.ui.viewmodels.auth.RegisterViewModel
import com.example.betrun.ui.viewmodels.home.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {
    factory<AuthRepository> {
        AuthRepositoryImpl(
            auth = Firebase.auth
        )
    }
    factory<ProfileRepository> {
        ProfileRepositoryImpl(
            db = FirebaseDatabase.getInstance().reference,
            storage = FirebaseStorage.getInstance()
        )
    }
    factory<ProgramRepository> {
        ProgramRepositoryImpl(
            db = FirebaseDatabase.getInstance().reference
        )
    }
    factory<WorkoutRepository> {
        WorkoutRepositoryImpl(
            db = FirebaseDatabase.getInstance().reference,
            storage = FirebaseStorage.getInstance()
        )
    }
    factory<QuestionsRepository> {
        QuestionsRepositoryImpl(
            db = FirebaseDatabase.getInstance().reference
        )
    }

    single { IsLoggedInUseCase(authRepository = get()) }
    single { GetUserUidUseCase(authRepository = get()) }
    single { LoginUseCase(authRepository = get()) }
    single { LogoutUseCase(authRepository = get()) }
    single { RegisterUseCase(authRepository = get()) }
    single { ResetPasswordUseCase(authRepository = get()) }

    single { GetRunsUseCase(repo = get()) }
    single { GetPersonDataUseCase(repo = get()) }
    single { GetProfilePicUseCase(repo = get()) }
    single { GetWeightChartDataUseCase(repo = get()) }
    single { GetActiveDaysUseCase(repo = get()) }

    single { SetPersonDataUseCase(repo = get()) }
    single { SetCurrentWeightUseCase(repo = get()) }
    single { SetProfilePicUseCase(repo = get()) }
    single { SetNewRunUseCase(repo = get()) }

    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { LoginViewModel(loginUseCase = get(), resetPasswordUseCase = get()) }
    viewModel { RegisterViewModel(registerUseCase = get()) }
    viewModel { AuthenticationNavigationViewModel(isLoggedInUseCase = get()) }
}