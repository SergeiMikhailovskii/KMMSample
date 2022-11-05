package com.fitnest.android.style

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val PoppinsStyle = TextStyle(
    fontFamily = poppinsFamily
)

val PoppinsMediumStyle = PoppinsStyle.copy(
    fontWeight = FontWeight.Medium
)

val PoppinsMediumStyle8 = PoppinsMediumStyle.copy(
    fontSize = TextSize.Size8
)

val PoppinsBoldStyle = PoppinsStyle.copy(
    fontWeight = FontWeight.Bold
)

val PoppinsBoldStyle20 = PoppinsBoldStyle.copy(
    fontSize = TextSize.Size20
)

val PoppinsBoldStyle20Black = PoppinsBoldStyle20.copy(
    color = BlackColor
)

val PoppinsBoldStyle16 = PoppinsBoldStyle.copy(
    fontSize = TextSize.Size16
)

val ErrorStyle
    @Composable
    get() = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.error
    )

val BodyLargeOnBackground
    @Composable
    get() = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground
    )

val TitleMediumOnBackground
    @Composable
    get() = MaterialTheme.typography.titleMedium.copy(
        color = MaterialTheme.colorScheme.onBackground
    )