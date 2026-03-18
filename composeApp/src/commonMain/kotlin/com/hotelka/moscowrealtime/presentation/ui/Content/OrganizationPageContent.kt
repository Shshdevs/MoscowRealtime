package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.OrganizationGraphEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationHomePageEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationNavEvents
import com.hotelka.moscowrealtime.presentation.model.OrganizationGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.OrganizationState
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ChangeOrganizationNameAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.QuitOrganizationAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.OrganizationGroupsListContent
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.OrganizationSettingsForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.OrganizersListContent
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.TabbedPager
import com.hotelka.moscowrealtime.presentation.ui.Custom.LoadingText
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import moscowrealtime.composeapp.generated.resources.group
import moscowrealtime.composeapp.generated.resources.groups
import moscowrealtime.composeapp.generated.resources.loading_organization_error
import moscowrealtime.composeapp.generated.resources.manager
import moscowrealtime.composeapp.generated.resources.organizers
import moscowrealtime.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OrganizationPageContent(
    orgGraphUiModel: OrganizationGraphUiModel,
    onEvent: (OrganizationGraphEvents) -> Unit
) {
    val tabs = listOf(
        Pair(Res.drawable.group, Res.string.groups),
        Pair(Res.drawable.manager, Res.string.organizers),
        Pair(Res.drawable.settings, Res.string.settings)
    )

    Box {
        Column(
            Modifier.fillMaxSize().background(background)
        ) {
            MediumTopAppBar(
                title = {
                    when (orgGraphUiModel.organization) {
                        is OrganizationState.Error -> {
                            Text(
                                stringResource(Res.string.loading_organization_error),
                                fontWeight = FontWeight.SemiBold,
                                color = titleTextColor.copy(0.8f)
                            )
                        }

                        OrganizationState.Loading -> LoadingText(
                            modifier = Modifier.fillMaxWidth(),
                            textSize = 22.sp,
                            color = titleTextColor.copy(0.2f)
                        )

                        is OrganizationState.Success -> {
                            Text(
                                orgGraphUiModel.organization.organization.name,
                                fontWeight = FontWeight.SemiBold,
                                color = titleTextColor.copy(0.8f),
                                fontSize = 22.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(OrganizationNavEvents.OnNavigateBack) }
                    ) {
                        Icon(
                            painterResource(Res.drawable.arrow_previous),
                            "Go back",
                            tint = titleTextColor.copy(0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(background),
                modifier = Modifier.padding(end = 40.dp)
            )
            Spacer(Modifier.height(40.dp))
            TabbedPager(
                tabsLabels = tabs,
            ) { page ->
                when (page) {
                    0 -> {
                        OrganizationGroupsListContent(
                            orgGraphUiModel = orgGraphUiModel,
                            onSeeMyGroups = { onEvent(OrganizationNavEvents.NavigateMyGroups) },
                            onNavigateGroup = { onEvent(OrganizationNavEvents.OnNavigateGroup(it)) },
                            onRetryLoad = { onEvent(OrganizationHomePageEvents.OnReload) }
                        )
                    }

                    1 -> {
                        OrganizersListContent(
                            organizationPageUiState = orgGraphUiModel,
                            openOrganizerPage = {
                                onEvent(
                                    OrganizationNavEvents.OnNavigateOrganizerPage(
                                        it
                                    )
                                )
                            },
                            onRetryLoad = { onEvent(OrganizationHomePageEvents.OnReload) }
                        )
                    }

                    2 -> {
                        OrganizationSettingsForm(
                            organizationPageUiState = orgGraphUiModel,
                            onEvent
                        )
                    }
                }
            }
        }

        if (orgGraphUiModel.homePageUiModel.showChangeNameDialog) {
            ChangeOrganizationNameAlert(
                onChangeOrganizationName = { onEvent(OrganizationHomePageEvents.OnChangeOrgName(it)) },
                onDismiss = { onEvent(OrganizationHomePageEvents.OnChangeShowOrgNameDialog) }
            )
        }
        if (orgGraphUiModel.homePageUiModel.showQuitOrgDialog) {
            QuitOrganizationAlert(
                onSubmit = { onEvent(OrganizationHomePageEvents.OnQuitOrg) },
                onDismiss = { onEvent(OrganizationHomePageEvents.OnChangeShowQuitOrgDialog) }
            )
        }
    }
}