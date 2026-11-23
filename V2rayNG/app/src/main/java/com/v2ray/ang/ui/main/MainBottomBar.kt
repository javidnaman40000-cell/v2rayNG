
package com.v2ray.ang.ui.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.v2ray.ang.R
import kotlinx.coroutines.launch

private val NexusBlue = Color(0xFF00D9FF)
private val NexusDark = Color(0xFF07111F)
private val NexusGreen = Color(0xFF00E6A0)
private val NexusRed = Color(0xFFFF496C)

@Composable
fun MainBottomBar(
    displayText: String,
    isRunning: Boolean,
    isDarkTheme: Boolean,
    onAction: (MainAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val rotationAnim = remember { Animatable(0f) }

    LaunchedEffect(isRunning) {
        if (!isRunning) rotationAnim.snapTo(0f)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .shadow(14.dp, CircleShape)
                .clip(CircleShape)
                .background(NexusDark)
                .border(1.dp, NexusBlue.copy(alpha = 0.55f), CircleShape)
                .clickable {
                    onAction(MainAction.TestCurrentServer)
                }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = {
                    if (!isRunning) {
                        scope.launch {
                            rotationAnim.animateTo(
                                720f,
                                tween(3000)
                            )
                        }
                    }
                    onAction(MainAction.ToggleService)
                },
                modifier = Modifier.size(62.dp),
                shape = CircleShape,
                containerColor = if (isRunning) NexusRed else NexusBlue
            ) {
                Icon(
                    painter = painterResource(
                        if (isRunning) R.drawable.ic_stop_24dp
                        else R.drawable.ic_play_24dp
                    ),
                    contentDescription = stringResource(
                        if (isRunning) R.string.acc_stop
                        else R.string.acc_start
                    ),
                    tint = Color.Black,
                    modifier = Modifier
                        .size(27.dp)
                        .graphicsLayer {
                            rotationZ = rotationAnim.value
                        }
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isRunning) "NEXUS CONNECTED"
                    else "NEXUS VPN",
                    color = NexusBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.2.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = displayText,
                    color = if (isRunning) NexusGreen
                    else Color(0xFF9CB3C9),
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.semantics {
                        contentDescription = displayText
                    }
                )
            }

            Spacer(Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(9.dp)
                    .background(
                        if (isRunning) NexusGreen else NexusRed,
                        CircleShape
                    )
            )
        }
    }
}
