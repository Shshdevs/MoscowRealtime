package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.VisualTransformation
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.errorColor

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    errorDescription: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    color: Color = background
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = if (isError) errorDescription else if (!singleLine) supportingText else null,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = if (!singleLine) 50 else 1,
        singleLine = singleLine,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Transparent,
            errorContainerColor = Transparent,
            unfocusedContainerColor = Transparent,

            errorLabelColor = errorColor,
            errorTextColor = errorColor,
            errorSupportingTextColor = errorColor,
            errorIndicatorColor = errorColor,
            errorTrailingIconColor = errorColor,
            errorCursorColor = errorColor,
            errorLeadingIconColor = errorColor,
            focusedLeadingIconColor = color.copy(0.7f),
            unfocusedLeadingIconColor = color.copy(0.7f),
            cursorColor = color.copy(0.7f),
            unfocusedTextColor = color.copy(0.7f),
            focusedTextColor = color.copy(0.7f),
            focusedSupportingTextColor = color.copy(0.7f),
            unfocusedSupportingTextColor = color.copy(0.7f),
            focusedIndicatorColor = color.copy(0.7f),
            unfocusedIndicatorColor = color.copy(0.7f),
            focusedTrailingIconColor = color.copy(0.7f),
            unfocusedTrailingIconColor = color.copy(0.7f),
            focusedLabelColor = color.copy(0.7f),
            unfocusedLabelColor = color.copy(0.7f),
            selectionColors = TextSelectionColors(
                handleColor = color.copy(1f),
                backgroundColor = color.copy(0.1f)
            ),
        )
    )
}