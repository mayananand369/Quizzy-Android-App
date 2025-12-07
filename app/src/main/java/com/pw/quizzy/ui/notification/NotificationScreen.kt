package com.pw.quizzy.ui.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.auth0.android.Auth0
import com.pw.quizzy.R
import com.pw.quizzy.ui.theme.GreenLight
import com.pw.quizzy.ui.theme.PurpleLight
import com.pw.quizzy.ui.theme.QuizzyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current

    val auth0 = remember {
        Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar
        TopAppBar(
            title = { Text("Notifications & Settings") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Notifications section
            Text(
                text = "Notifications",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            NotificationItem(
                title = "Missed quiz in physics in yesterday",
                time = "2 hours ago",
                color = Color(0xFFFFE0E0)
            )

            NotificationItem(
                title = "Badge earned",
                time = "8 hours ago",
                color = PurpleLight
            )

            NotificationItem(
                title = "Teacher Note",
                time = "1 day ago",
                color = GreenLight
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            SettingsItem(
                iconPainter = painterResource(id = R.drawable.switch_child),
                title = "Switch Child",
                subtitle = "Change active child profile"
            )

            SettingsItem(
                iconPainter = painterResource(id = R.drawable.language_icon),
                title = "Language",
                subtitle = "English"
            )
            SettingsItem(
                iconPainter = painterResource(id = R.drawable.logout),
                title = "Logout",
                subtitle = "Sign out of your account",
                onClick = {
                    // Your logout logic here
                    viewModel.logout(context, auth0, onLogout)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Composable
fun NotificationItem(
    title: String,
    time: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SettingsItem(
    iconPainter: Painter,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = iconPainter,
            contentDescription = title,
            modifier = Modifier
                .size(28.dp)
                .padding(end = 16.dp),
            colorFilter = ColorFilter.tint(Color(0xFF1B2124)) // normal icon color
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationsScreenPreview() {
    QuizzyTheme {
        NotificationsScreen(
            onBack = {},
            onLogout = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationItemPreview() {
    QuizzyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationItem(
                title = "Missed quiz in physics in yesterday",
                time = "2 hours ago",
                color = Color(0xFFFFE0E0)
            )
            NotificationItem(
                title = "Badge earned",
                time = "8 hours ago",
                color = PurpleLight
            )
            NotificationItem(
                title = "Teacher Note",
                time = "1 day ago",
                color = GreenLight
            )
        }
    }
}