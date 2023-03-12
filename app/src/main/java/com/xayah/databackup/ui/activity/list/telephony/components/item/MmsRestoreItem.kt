package com.xayah.databackup.ui.activity.list.telephony.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.xayah.databackup.R
import com.xayah.databackup.data.MmsItem
import com.xayah.databackup.ui.activity.guide.components.card.SerialIcon
import com.xayah.databackup.ui.activity.guide.components.card.SerialText
import com.xayah.databackup.ui.components.BodySmallText
import com.xayah.databackup.ui.components.LabelSmallText
import com.xayah.databackup.ui.components.TitleMediumText
import com.xayah.databackup.ui.components.paddingTop
import com.xayah.databackup.util.Dates

@ExperimentalMaterial3Api
@Composable
fun MmsRestoreItem(item: MmsItem) {
    val smallPadding = dimensionResource(R.dimen.padding_small)
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val onClick = { it: Boolean ->
        item.isSelected.value = it
    }

    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    if (item.isOnThisDevice.value.not())
                        onClick(item.isSelected.value.not())
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(mediumPadding, smallPadding)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        TitleMediumText(text = item.address)
                        LabelSmallText(
                            text = Dates.getShortRelativeTimeSpanString(
                                if (item.pdu.date.toString().length == 10)
                                    item.pdu.date * 1000
                                else
                                    item.pdu.date
                            ).toString(),
                            bold = false
                        )
                    }
                    IconToggleButton(
                        checked = item.isSelected.value,
                        enabled = item.isOnThisDevice.value.not(),
                        onCheckedChange = { onClick(it) }) {
                        if (item.isSelected.value) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null
                            )
                        }
                    }
                }
                BodySmallText(
                    modifier = Modifier.paddingTop(smallPadding),
                    text = item.content,
                    bold = false
                )
                Row(
                    modifier = Modifier.paddingTop(smallPadding),
                    horizontalArrangement = Arrangement.spacedBy(smallPadding)
                ) {
                    SerialIcon(
                        icon = if (item.type == 151L)
                            ImageVector.vectorResource(id = R.drawable.ic_call_received)
                        else
                            ImageVector.vectorResource(id = R.drawable.ic_call_made)
                    )
                    if (item.isOnThisDevice.value)
                        SerialText(serial = stringResource(R.string.restored))
                }
            }
        }
    }
}