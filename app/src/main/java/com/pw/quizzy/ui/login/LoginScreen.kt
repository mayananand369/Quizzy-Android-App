package com.pw.quizzy.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.auth0.android.Auth0
import com.pw.quizzy.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    var schoolId by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }

    val auth0 = remember {
        Auth0.getInstance(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Box(
            modifier = Modifier
                .size(width = 46.dp, height = 46.dp)
                .align(Alignment.TopStart)
                .offset(x = (-29).dp, y = 52.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFB6C1))
        )

        Box(
            modifier = Modifier
                .size(width = 46.dp, height = 46.dp)
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = 26.dp)
                .clip(CircleShape)
                .background(Color(0xFFB0E0E6))
        )

        Box(
            modifier = Modifier
                .size(width = 13.8.dp, height = 13.8.dp)
                .align(Alignment.CenterStart)
                .offset(x = (15).dp, y = (-150).dp)
                .clip(CircleShape)
                .background(Color(0xFFDFF8FB))
        )

        Box(
            modifier = Modifier
                .size(width = 13.8.dp, height = 13.8.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-20).dp, y = (-100).dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF4B0))
        )

        Image(
            painter = painterResource(id = R.drawable.pw_logo),
            contentDescription = "PW Logo",
            modifier = Modifier
                .size(width = 18.dp, height = 18.dp)
                .align(Alignment.TopCenter)
                .offset(x = 40.dp, y = 50.dp),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.pi_logo),
            contentDescription = "Pi Logo",
            modifier = Modifier
                .size(width = 27.dp, height = 24.dp)
                .align(Alignment.CenterStart)
                .offset(x = 0.dp, y = (-80).dp)
                .padding(vertical = 0.dp),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.top_creative),
            contentDescription = "Avatars",
            modifier = Modifier
                .size(width = 550.dp, height = 450.dp)
                .align(Alignment.TopCenter)
                .padding(vertical = 0.dp),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(393.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Welcome to\nQuizzy!",
                    fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W700,
                    color = Color(0XFFFFFFFF),
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (loginState is LoginState.Error) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = (loginState as LoginState.Error).message,
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 0.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                            .padding(bottom = 80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Let's Get you Signed in",
                            fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = Color(0xFF1B2124),
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        OutlinedTextField(
                            value = schoolId,

                            onValueChange = { schoolId = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            placeholder = {
                                Text(
                                    "School ID",
                                    fontFamily = FontFamily(Font(R.font.reddit_sans_regular)),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    color = Color(0xFF757575),
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0Xff757575),
                                unfocusedBorderColor = Color(0XffD9DCE1),
                                focusedContainerColor = Color(0xffF9F9F9),
                                unfocusedContainerColor = Color(0xFFF9F9F9),
                                focusedTextColor = Color(0xff1B2124),
                                unfocusedTextColor = Color(0xff1B2124)
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = studentId,
                            onValueChange = { studentId = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 0.dp),
                            placeholder = {
                                Text(
                                    "Student ID",
                                    fontFamily = FontFamily(Font(R.font.reddit_sans_regular)),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    color = Color(0xFF757575),
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0Xff757575),
                                unfocusedBorderColor = Color(0XffD9DCE1),
                                focusedTextColor = Color(0xff1B2124),
                                unfocusedTextColor = Color(0xff1B2124)
                            ),
                            singleLine = true
                        )

                        if (loginState is LoginState.Loading) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Signing you in...",
                                    fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                                    fontWeight = FontWeight.W600,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1B2124)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(33.dp))

                Button(
                    onClick = {
                        if (schoolId.isNotBlank() && studentId.isNotBlank()) {
                            viewModel.login(context, auth0)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 120.dp)
                        .height(60.dp)
                        .offset(y = 5.dp),

                    shape = RoundedCornerShape(
                        topStart = 60.dp,
                        topEnd = 60.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    ),
                    enabled = loginState !is LoginState.Loading &&
                            schoolId.isNotBlank() &&
                            studentId.isNotBlank(),
                ) {
                    Text(
                        text = "Sign in",
                        fontFamily = FontFamily(Font(R.font.reddit_sans_semi_bold)),
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFFFFFFFF),
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginSuccess = {}
    )
}