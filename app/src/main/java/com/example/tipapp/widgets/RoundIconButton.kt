package com.example.tipapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val ButtonSizeModifier=Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier =Modifier,
    imageVector: ImageVector,
    onClick:() -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor:Color=MaterialTheme.colors.background,
    elevation: Dp = 4.dp
){

    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick.invoke() }
            .then(ButtonSizeModifier),
        shape = CircleShape,
        backgroundColor = backgroundColor,
        elevation=elevation

    ) {

        Icon(imageVector = imageVector, contentDescription = "plus minus icon",tint=tint)
    }

}