package com.v2ray.ang.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import com.v2ray.ang.R
import com.v2ray.ang.ui.compose.AppTopBar
import com.v2ray.ang.ui.compose.verticalScrollbar

private val NexusBlue = Color(0xFF00D9FF)
private val NexusDark = Color(0xFF0B1B2D)
private val NexusMuted = Color(0xFF91A8BE)

@Composable
fun MainTopBar(
    isLoading: Boolean,
    showSearch: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClose: () -> Unit,
    onSearchToggle: (Boolean) -> Unit,
    onMenuClick: () -> Unit,
    onAction: (MainAction) -> Unit,
    onMoreMenuAction: (MainMoreMenuAction) -> Unit
) {
    var showImportMenu by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    val importMenuScrollState = rememberScrollState()
    val moreMenuScrollState = rememberScrollState()

    val statusBarHeight =
        WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val navBarHeight =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val maxMenuHeight =
        LocalConfiguration.current.screenHeightDp.dp -
        statusBarHeight - navBarHeight - 20.dp

    if (showSearch) {
        AppTopBar(
            title = "NEXUS VPN",
            onBackClick = {},
            isLoading = isLoading,
            isSearchActive = true,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onSearchClose = onSearchClose,
            searchPlaceholder = stringResource(R.string.menu_item_search),
            navigationIcon = {
                IconButton(onClick = onSearchClose) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_24dp),
                        contentDescription = stringResource(R.string.acc_back),
                        tint = NexusBlue
                    )
                }
            }
        )
    } else {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = NexusDark
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu_24dp),
                            contentDescription = stringResource(R.string.acc_open_menu),
                            tint = NexusBlue,
                            modifier = Modifier.size(25.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "NEXUS VPN",
                            color = NexusBlue,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "SECURE CONNECTION",
                            color = NexusMuted,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 2.sp
                        )
                    }

                    IconButton(onClick = { onSearchToggle(true) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search_24dp),
                            contentDescription = stringResource(R.string.acc_search),
                            tint = NexusBlue
                        )
                    }

                    Box {
                        IconButton(onClick = { showImportMenu = true }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add_24dp),
                                contentDescription = stringResource(R.string.acc_add),
                                tint = NexusBlue
                            )
                        }

                        DropdownMenu(
                            expanded = showImportMenu,
                            onDismissRequest = { showImportMenu = false },
                            scrollState = importMenuScrollState,
                            containerColor = NexusDark,
                            modifier = Modifier
                                .heightIn(max = maxMenuHeight)
                                .verticalScrollbar(importMenuScrollState)
                        ) {
                            ImportMenuContent(
                                onAction = { action ->
                                    showImportMenu = false
                                    onAction(action)
                                }
                            )
                        }
                    }

                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more_vert_24dp),
                                contentDescription = stringResource(R.string.acc_more),
                                tint = NexusBlue
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            scrollState = moreMenuScrollState,
                            containerColor = NexusDark,
                            modifier = Modifier
                                .heightIn(max = maxMenuHeight)
                                .verticalScrollbar(moreMenuScrollState)
                        ) {
                            MoreMenuContent { action ->
                                showMenu = false
                                onMoreMenuAction(action)
                            }
                        }
                    }
                }

                if (isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = NexusBlue,
                        trackColor = NexusDark
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(NexusBlue.copy(alpha = 0.25f))
                    )
                }
            }
        }
    }
}
