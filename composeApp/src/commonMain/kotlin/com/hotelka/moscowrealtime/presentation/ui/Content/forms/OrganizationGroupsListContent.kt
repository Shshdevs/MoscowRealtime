package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.presentation.model.OrganizationGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.OrganizationState
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.MyGroupsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.items.GroupItemCard
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NoGroupsItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_groups
import moscowrealtime.composeapp.generated.resources.loading_groups
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrganizationGroupsListContent(
    orgGraphUiModel: OrganizationGraphUiModel,
    onSeeMyGroups: () -> Unit,
    onRetryLoad: () -> Unit,
    onNavigateGroup: (GroupDetailed) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        when (orgGraphUiModel.organization) {
            is OrganizationState.Error -> {
                item("error") {
                    ErrorForm(
                        Modifier.fillMaxSize(),
                        Res.string.error_loading_groups,
                        onRetryLoad
                    )
                }
            }

            is OrganizationState.Loading -> {
                item("loading") {
                    LoadingForm(Modifier.fillMaxSize(), stringResource(Res.string.loading_groups))
                }
            }

            is OrganizationState.Success -> {
                item { Spacer(Modifier) }
                item {
                    MyGroupsCard(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        onSeeMyGroups
                    )
                }
                if (orgGraphUiModel.organizationGroups.isNotEmpty()) {
                    items(orgGraphUiModel.organizationGroups) { group ->
                        GroupItemCard(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            group,
                            navigateGroup = onNavigateGroup
                        )
                    }
                } else {
                    item {
                        Spacer(Modifier.height(10.dp))
                        NoGroupsItem()
                    }
                }
            }
        }
    }
}