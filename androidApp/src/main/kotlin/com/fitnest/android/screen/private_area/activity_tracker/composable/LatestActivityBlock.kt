package com.fitnest.android.screen.private_area.activity_tracker.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fitnest.android.R
import com.fitnest.android.screen.private_area.activity_tracker.ActivityTrackerViewModel
import com.fitnest.android.screen.private_area.activity_tracker.data.ActivityTrackerScreenData
import com.fitnest.android.style.Dimen
import com.fitnest.android.style.GrayColor2
import com.fitnest.android.style.Padding
import com.fitnest.android.style.PoppinsMediumStyle12Black
import com.fitnest.android.style.PoppinsNormalStyle10Gray2
import com.fitnest.android.style.PoppinsSemiBoldStyle16Black
import com.fitnest.domain.enum.ActivityType

@Preview
@Composable
internal fun LatestActivityBlockPreview() {
    LatestActivityBlock(
        modifier = Modifier,
        viewModel = Any() as ActivityTrackerViewModel,
        activities = listOf(
            ActivityTrackerScreenData.Activity(
                1,
                "Drinking 300ml Water",
                "About 3 minutes ago",
                ActivityType.WATER
            ),
            ActivityTrackerScreenData.Activity(
                2,
                "Drinking 300ml Water",
                "About 3 minutes ago",
                ActivityType.WATER
            ),
            ActivityTrackerScreenData.Activity(
                3,
                "Drinking 300ml Water",
                "About 3 minutes ago",
                ActivityType.WATER
            ),
        )
    )
}

@Preview
@Composable
internal fun LatestActivityItemPreview() {
    LatestActivityItem(
        ActivityTrackerScreenData.Activity(
            1,
            "Drinking 300ml Water",
            "About 3 minutes ago",
            ActivityType.WATER
        ),
        Any() as ActivityTrackerViewModel
    )
}

@Composable
internal fun LatestActivityBlock(
    viewModel: ActivityTrackerViewModel,
    modifier: Modifier,
    activities: List<ActivityTrackerScreenData.Activity>? = null
) {
    Column(modifier = modifier) {
        Text(
            stringResource(id = R.string.private_area_activity_tracker_screen_latest_activity_title),
            style = PoppinsSemiBoldStyle16Black
        )
        activities?.forEach {
            LatestActivityItem(activity = it, viewModel = viewModel)
        }
    }
}

@Composable
private fun LatestActivityItem(
    activity: ActivityTrackerScreenData.Activity,
    viewModel: ActivityTrackerViewModel
) {
    var isMoreMenuExpanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(top = Padding.Padding15)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimen.Dimen16),
        elevation = Dimen.Dimen20
    ) {
        Row(
            modifier = Modifier.padding(Padding.Padding15),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_private_area_notification_meal),
                contentDescription = null
            )
            Column(modifier = Modifier.padding(start = Padding.Padding8)) {
                Text(activity.title, style = PoppinsMediumStyle12Black)
                Text(
                    activity.description,
                    modifier = Modifier.padding(top = Padding.Padding3),
                    style = PoppinsNormalStyle10Gray2
                )
            }
            Box(modifier = Modifier.weight(1F))
            Box {
                IconButton(onClick = { isMoreMenuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null, tint = GrayColor2)
                }
                DropdownMenu(
                    expanded = isMoreMenuExpanded,
                    onDismissRequest = { isMoreMenuExpanded = false }
                ) {
                    DropdownMenuItem(onClick = {
                        isMoreMenuExpanded = false
                        viewModel.deleteActivity(activity)
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
