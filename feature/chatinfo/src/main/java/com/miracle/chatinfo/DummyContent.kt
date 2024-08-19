package com.miracle.chatinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors

@Composable
fun MediaContent(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(100) {
            Box(
                Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .background(mColors.secondary.copy(alpha = 0.3f))
            )
        }
    }
}

@Composable
fun FilesContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(lSpacing.small),
        contentPadding = PaddingValues(lSpacing.small)
    ) {
        items(100) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .size(40.dp)
                        .background(
                            mColors.secondary.copy(alpha = 0.3f),
                            RoundedCornerShape(20)
                        )
                )

                Spacer(modifier = Modifier.width(lSpacing.medium))

                Column {
                    Box(
                        Modifier
                            .height(10.dp)
                            .fillMaxWidth(0.3f)
                            .background(
                                mColors.secondary.copy(alpha = 0.3f),
                                RoundedCornerShape(20)
                            )
                    )

                    Spacer(modifier = Modifier.height(lSpacing.small))

                    Box(
                        Modifier
                            .height(10.dp)
                            .fillMaxWidth(0.4f)
                            .background(
                                mColors.secondary.copy(alpha = 0.3f),
                                RoundedCornerShape(20)
                            )
                    )
                }
            }
        }
    }
}


@Composable
fun LinksContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(lSpacing.small),
        contentPadding = PaddingValues(lSpacing.small)
    ) {
        items(100) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(
                            mColors.secondary.copy(alpha = 0.3f),
                            RoundedCornerShape(20)
                        )
                )

                Spacer(modifier = Modifier.width(lSpacing.medium))

                Column {
                    Box(
                        Modifier
                            .height(60.dp)
                            .fillMaxWidth(0.9f)
                            .background(
                                mColors.secondary.copy(alpha = 0.3f),
                                RoundedCornerShape(20)
                            )
                    )

                    Spacer(modifier = Modifier.height(lSpacing.small))

                    Box(
                        Modifier
                            .height(10.dp)
                            .fillMaxWidth(0.6f)
                            .background(
                                mColors.secondary.copy(alpha = 0.3f),
                                RoundedCornerShape(20)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun VoiceContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(lSpacing.small),
        contentPadding = PaddingValues(lSpacing.small)
    ) {
        items(100) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .size(40.dp)
                        .background(
                            mColors.secondary.copy(alpha = 0.3f),
                            CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(lSpacing.medium))

                Column {
                    Box(
                        Modifier
                            .height(10.dp)
                            .fillMaxWidth(0.5f)
                            .background(
                                mColors.secondary.copy(alpha = 0.3f),
                                RoundedCornerShape(20)
                            )
                    )

                    Spacer(modifier = Modifier.height(lSpacing.small))

                    Box(
                        Modifier
                            .height(10.dp)
                            .fillMaxWidth(0.3f)
                            .background(
                                mColors.secondary.copy(alpha = 0.3f),
                                RoundedCornerShape(20)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun GroupsContent(modifier: Modifier = Modifier) {
    VoiceContent(modifier)
}