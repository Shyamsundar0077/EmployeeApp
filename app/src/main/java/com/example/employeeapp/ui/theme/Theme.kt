package com.example.employeeapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography

private val LightColors = lightColorScheme(
    primary = EcoGreen,
    onPrimary = Color.White,
    primaryContainer = LightEcoGreen,
    onPrimaryContainer = Color.Black,
    background = EcoBackground,
    onBackground = Color.Black
)

@Composable
fun EmployeeAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
