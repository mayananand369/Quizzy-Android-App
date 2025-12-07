package com.pw.quizzy.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pw.quizzy.R
import com.pw.quizzy.data.model.Accuracy
import com.pw.quizzy.data.model.Availability
import com.pw.quizzy.data.model.OverallAccuracy
import com.pw.quizzy.data.model.PerformanceByTopic
import com.pw.quizzy.data.model.Quiz
import com.pw.quizzy.data.model.QuizStreakDay
import com.pw.quizzy.data.model.RecommendedVideo
import com.pw.quizzy.data.model.Student
import com.pw.quizzy.data.model.StudentDashboard
import com.pw.quizzy.data.model.TodaySummary
import com.pw.quizzy.data.model.WeeklyOverview
import com.pw.quizzy.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToNotifications: () -> Unit, viewModel: HomeViewModel = viewModel()
) {
    val dashboardState by viewModel.dashboardState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { }, actions = {
                IconButton(onClick = onNavigateToNotifications) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications"
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
            )
        }) { padding ->
        when (val state = dashboardState) {
            is DashboardState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DashboardState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.refreshDashboard() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            is DashboardState.Success -> {
                DashboardContent(
                    data = state.data, modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun DashboardContent(
    data: StudentDashboard, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Pushes texts to start, icon to end
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = "Hello ${data.student.name}!",
                    fontFamily = FontFamily(Font(R.font.reddit_sans_bold)),
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF1B2124)
                )
                Text(
                    text = data.student.`class`,
                    fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF757575)
                )
            }

            IconButton(
                onClick = {
                    // Navigate to NotificationsScreen
                    // onNotificationClick()
                }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Open Notifications",
                    tint = Color(0xFF1B2124),
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Status cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatusCard(
                title = "Availability",
                value = data.student.availability.status,
                backgroundColor = Color(0xffF2FFF4),
                modifier = Modifier.weight(1f)
            )

            StatusCard(
                title = "Quiz",
                value = "${data.student.quiz.attempts} Attempt",
                backgroundColor = Color(0xffFFFCF9),
                modifier = Modifier.weight(1f),
            )

            StatusCard(
                title = "Accuracy",
                value = data.student.accuracy.current,
                backgroundColor = Color(0xffFFF6F6),
                modifier = Modifier.weight(1f)
            )
        }

        // Today's Summary
        Text(
            text = "Today's Summary",
            fontFamily = FontFamily(Font(R.font.reddit_sans_bold)),
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color(0xff1B2124),
            modifier = Modifier.padding(bottom = 5.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xffFCF7FF)
            ),
            border = BorderStroke(0.5.dp, Color(0xff996EB5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎯", fontSize = 60.sp, modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = data.todaySummary.mood,
                    fontFamily = FontFamily(Font(R.font.reddit_sans_bold)),
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    color = Color(0xff996EB5),
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = data.todaySummary.description,
                    fontFamily = FontFamily(Font(R.font.reddit_sans_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = Color(0xff1B2124),
                    modifier = Modifier.padding(bottom = 14.dp)
                )

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1B2124)
                    )
                ) {
                    Row {
                        Text(
                            text = "▶️"
                        )
                        Text(
                            text = data.todaySummary.recommendedVideo.actionText,
                            fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                            fontWeight = FontWeight.W600,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = Color(0xffFFFFFF),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Weekly Overview
        Text(
            text = "Weekly Overview",
            fontFamily = FontFamily(Font(R.font.reddit_sans_bold)),
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color(0xff1B2124),
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Quiz Streak
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.5.dp, Color(0xff7B7F86)),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quiz Streak",
                        fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color(0xff1B2124),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.quiz_streak_icon),
                        contentDescription = "Quiz Streak Icon",
                        modifier = Modifier
                            .size(width = 40.dp, height = 31.dp)
                            .padding(vertical = 0.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0xFF1B2124))
                            )
                        ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    data.weeklyOverview.quizStreak.forEach { day ->
                        DayCircle(
                            day = day.day, isDone = day.status == "done"
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Accuracy",
                        fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color(0xff1B2124),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.accuracy_icon),
                        contentDescription = "Accuracy Icon",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 0.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0xFF1B2124))
                            )
                        ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = data.weeklyOverview.overallAccuracy.label,
                    fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                    fontWeight = FontWeight.W600,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = Color(0xff1B2124),
                )

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = data.weeklyOverview.overallAccuracy.percentage / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFFF6776),
                    trackColor = Color(0xFFFFE0E3)
                )
            }

            // Performance by Topic
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Performance by Topic",
                        fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color(0xff1B2124),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.performance_icon),
                        contentDescription = "Performance Icon",
                        modifier = Modifier
                            .size(width = 34.dp, height = 34.dp)
                            .padding(vertical = 0.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0xFF1B2124))
                            )
                        ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                data.weeklyOverview.performanceByTopic.forEach { topic ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = topic.topic, fontSize = 12.sp, modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = if (topic.trend == "up") Color(0xFF4CAF50) else Color(
                                        0xFFFF6B6B
                                    ), shape = CircleShape
                                ), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (topic.trend == "up") "▲" else "▼",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun StatusCard(
    title: String, value: String, backgroundColor: Color, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ), border = BorderStroke(
            0.5.dp, when (title) {
                "Availability" -> Color(0xff22C55D)
                "Quiz" -> Color(0xffFE9C3B)
                "Accuracy" -> Color(0xff22C55D)
                else -> Color(0xffFF4F4F)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = when (title) {
                    "Availability" -> "👤"
                    "Quiz" -> "📝"
                    "Accuracy" -> "🎯"
                    else -> ""
                }, fontSize = 24.sp, modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = title,
                fontFamily = FontFamily(Font(R.font.reddit_sans_regular)),
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = Color(0xff1B2124),
            )

            Text(
                text = value,
                fontFamily = when (value) {
                    "Availability" -> FontFamily(Font(R.font.reddit_sans_bold))
                    "Quiz" -> FontFamily(Font(R.font.reddit_sans_semi_bold))
                    "Accuracy" -> FontFamily(Font(R.font.reddit_sans_semi_bold))
                    else -> FontFamily(Font(R.font.reddit_sans_semi_bold))
                },
                fontWeight = when (title) {
                    "Availability" -> FontWeight.W700
                    "Quiz" -> FontWeight.W600
                    "Accuracy" -> FontWeight.W600
                    else -> FontWeight.W600
                },
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = when (title) {
                    "Availability" -> Color(0xff3EDB5E)
                    "Quiz" -> Color(0xff1B2124)
                    "Accuracy" -> Color(0xff1B2124)
                    else -> Color(0xff1B2124)
                },
            )
        }
    }
}

@Composable
fun DayCircle(
    day: String, isDone: Boolean
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = if (isDone) Color(0xFF22C55D) else Color.White, shape = CircleShape
            )
            .border(
                width = if (!isDone) 2.dp else 0.dp,
                color = if (!isDone) Color.LightGray else Color.Transparent,
                shape = CircleShape
            ), contentAlignment = Alignment.Center
    ) {
        if (isDone) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = "Check",
                modifier = Modifier.size(width = 24.dp, height = 24.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(
                text = day,
                fontSize = 12.sp,
                fontWeight = FontWeight.W600,
                color = Color(0xff757575)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    QuizzyTheme {
        // Create sample data for preview
        val sampleData = StudentDashboard(
            student = Student(
                name = "Gaurav",
                `class` = "10th Class",
                availability = Availability(status = "Present"),
                quiz = Quiz(attempts = 3),
                accuracy = Accuracy(current = "72%")
            ), todaySummary = TodaySummary(
                mood = "Focused",
                description = "Struggles with Apply-level Math today.",
                recommendedVideo = RecommendedVideo(
                    title = "Apply Pythagoras Theorem",
                    actionText = "Watch: Apply Pythagoras Theorem"
                ),
                characterImage = "focused_character.png"
            ), weeklyOverview = WeeklyOverview(
                quizStreak = listOf(
                    QuizStreakDay("M", "done"),
                    QuizStreakDay("T", "done"),
                    QuizStreakDay("W", "done"),
                    QuizStreakDay("T", "done"),
                    QuizStreakDay("F", "pending"),
                    QuizStreakDay("S", "pending"),
                    QuizStreakDay("S", "pending")
                ), overallAccuracy = OverallAccuracy(
                    percentage = 68, label = "68% correct"
                ), performanceByTopic = listOf(
                    PerformanceByTopic("Newton's Laws Of Motion", "up"),
                    PerformanceByTopic("Sources Of Energy", "up"),
                    PerformanceByTopic("Light Reflection And Refraction", "down")
                )
            )
        )

        DashboardContent(data = sampleData)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatusCardPreview() {
    QuizzyTheme {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatusCard(
                title = "Availability",
                value = "Present",
                backgroundColor = GreenLight,
                modifier = Modifier.weight(1f)
            )
            StatusCard(
                title = "Quiz",
                value = "3 Attempt",
                backgroundColor = Color(0xFFFFF0E6),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayCirclePreview() {
    QuizzyTheme {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DayCircle(day = "M", isDone = true)
            DayCircle(day = "T", isDone = true)
            DayCircle(day = "W", isDone = false)
            DayCircle(day = "T", isDone = false)
        }
    }
}