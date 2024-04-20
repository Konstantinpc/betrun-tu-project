package com.example.betrun.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.betrun.R

val AvenirNextFamily = FontFamily(
    Font(R.font.avenirnext_light, FontWeight.Light),
    Font(R.font.avenirnext_regular, FontWeight.Normal),
    Font(R.font.avenirnext_demi, FontWeight.SemiBold),
    Font(R.font.avenirnext_medium, FontWeight.Medium),
    Font(R.font.avenirnext_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 37.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 31.sp,
        letterSpacing = 2.38.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 25.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = AvenirNextFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp
    )
)