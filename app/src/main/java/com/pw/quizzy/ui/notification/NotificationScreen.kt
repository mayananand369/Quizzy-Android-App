package com.pw.quizzy.ui.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
        Auth0.getInstance(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Notifications & Settings",
                        fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color(0xff1B2124)
                    )
                }
            },

            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Notifications",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            NotificationItem(
                title = "Missed quiz in physics in yesterday",
                time = "2 hours ago",
                color = Color(0xFFFFF9F4),
                accentColor = Color(0xffFFB370)
            )

            Spacer(modifier = Modifier.height(10.dp))


            NotificationItem(
                title = "Badge earned",
                time = "8 hours ago",
                color = Color(0xffFBF4FF),
                accentColor = Color(0xff996EB5)
            )

            Spacer(modifier = Modifier.height(10.dp))

            NotificationItem(
                title = "Teacher Note",
                time = "1 day ago",
                color = Color(0xffF2FFF4),
                accentColor = Color(0xff22C55D)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Settings",
                fontFamily = FontFamily(Font(R.font.reddit_sans_bold)),
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color(0xff1B2124),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            SettingsItem(
                iconPainter = painterResource(id = R.drawable.switch_child),
                title = "Switch Child",
                subtitle = "Change active child profile"
            )

            Spacer(modifier = Modifier.height(10.dp))


            SettingsItem(
                iconPainter = painterResource(id = R.drawable.language_icon),
                title = "Language",
                subtitle = "English"
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItem(
                iconPainter = painterResource(id = R.drawable.logout),
                title = "Logout",
                subtitle = "Sign out of your account",
                onClick = {
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
    color: Color,
    accentColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(
                        color = accentColor,
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color(0xff1B2124),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = time,
                    fontFamily = FontFamily(Font(R.font.reddit_sans_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = Color(0xff757575)
                )
            }
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
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = iconPainter,
            contentDescription = title,
            modifier = Modifier
                .size(24.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color(0xff1B2124),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = subtitle,
                fontFamily = FontFamily(Font(R.font.reddit_sans_regular)),
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = Color(0xff757575),
                modifier = Modifier.padding(bottom = 4.dp)
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
                color = Color(0xFFFFE0E0),
                accentColor = Color(0xffFFB370)
            )
            NotificationItem(
                title = "Badge earned",
                time = "8 hours ago",
                color = PurpleLight,
                accentColor = Color(0xff996EB5)
            )
            NotificationItem(
                title = "Teacher Note",
                time = "1 day ago",
                color = GreenLight,
                accentColor = Color(0xff22C55D)
            )
        }
    }
}