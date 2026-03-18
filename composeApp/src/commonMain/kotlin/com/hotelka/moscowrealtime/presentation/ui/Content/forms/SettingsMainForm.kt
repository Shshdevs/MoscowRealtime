package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.items.SwitchAction
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.can_see_everyone
import moscowrealtime.composeapp.generated.resources.can_see_only_friends
import moscowrealtime.composeapp.generated.resources.change_password
import moscowrealtime.composeapp.generated.resources.edit
import moscowrealtime.composeapp.generated.resources.edit_profile_info
import moscowrealtime.composeapp.generated.resources.hide_only_photos
import moscowrealtime.composeapp.generated.resources.lock
import moscowrealtime.composeapp.generated.resources.private_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsMainForm(
    user: User,
    updatePrivateSettings:(Boolean, Boolean) -> Unit,
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit
) {
    SwitchAction(
        checked = user.profilePrivate,
        onCheckedChange = {
            updatePrivateSettings(it, if (it) true else user.photosPrivate)
        },
        label = Res.string.private_profile,
        subLabel = if (user.profilePrivate) Res.string.can_see_only_friends else Res.string.can_see_everyone
    )

    SwitchAction(
        checked = user.photosPrivate || user.profilePrivate,
        onCheckedChange = {
            updatePrivateSettings(false, it)
        },
        label = Res.string.hide_only_photos,
        subLabel = if (user.photosPrivate) Res.string.can_see_only_friends else Res.string.can_see_everyone
    )
    Spacer(Modifier.height(10.dp))
    OutlinedButton(
        onClick = onEditProfile,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(2.dp, blue),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Icon(
            painterResource(Res.drawable.edit),
            "Edit Profile Info",
            tint = blue
        )
        Spacer(Modifier.width(5.dp))
        Text(
            stringResource(Res.string.edit_profile_info),
            color = blue.copy(0.8f),
        )
    }

    Spacer(Modifier.height(5.dp))
    OutlinedButton(
        onClick = onChangePassword,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(2.dp, secondaryTextColor),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Icon(
            painterResource(Res.drawable.lock),
            "Lock",
            tint = secondaryTextColor
        )
        Spacer(Modifier.width(5.dp))
        Text(
            stringResource(Res.string.change_password),
            color = secondaryTextColor,
        )
    }
}
