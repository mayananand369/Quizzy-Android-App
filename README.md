# Quizzy - Student Dashboard App 📚

A modern Android application for students to track their academic progress, quiz performance, and learning activities.

## 🎯 Features

- **Auth0 Authentication** - Secure login/logout flow
- **Student Dashboard** - View performance metrics at a glance
- **Quiz Tracking** - Monitor quiz attempts and streaks
- **Performance Analytics** - Track accuracy and topic-wise performance
- **Weekly Overview** - Visual representation of learning progress
- **Notifications** - Stay updated with quiz reminders and achievements

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: Jetpack Compose
- **Authentication**: Auth0
- **Networking**: Retrofit + Gson
- **Navigation**: Jetpack Navigation Compose
- **Minimum SDK**: API 24 (Android 7.0)

## 📱 Screenshots

*Add screenshots here after taking them from your app*

## 🚀 Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Android SDK API 24 or higher
- Auth0 account (free tier works)

### Configuration

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/Quizzy-Android-App.git
   cd Quizzy-Android-App
   ```

2. **Configure Auth0**
    - Create an application in [Auth0 Dashboard](https://manage.auth0.com/)
    - Update `app/src/main/res/values/strings.xml`:
      ```xml
      <string name="com_auth0_domain">YOUR_AUTH0_DOMAIN</string>
      <string name="com_auth0_client_id">YOUR_CLIENT_ID</string>
      <string name="com_auth0_scheme">com.yourpackage.quizzy</string>
      ```

3. **Update Auth0 Callback URLs** (in Auth0 Dashboard):
    - **Allowed Callback URLs**:
      ```
      com.yourpackage.quizzy://YOUR_DOMAIN/android/com.yourpackage.quizzy/callback
      ```
    - **Allowed Logout URLs**:
      ```
      com.yourpackage.quizzy://YOUR_DOMAIN/android/com.yourpackage.quizzy/callback
      ```

4. **Update package name** in:
    - `build.gradle.kts` (Module: app) → `applicationId`
    - `LoginViewModel.kt` → `.withScheme()`
    - `HomeViewModel.kt` → `.withScheme()`

5. **Sync and Build**
   ```bash
   ./gradlew build
   ```

## 📂 Project Structure

```
app/src/main/java/com/yourpackage/quizzy/
├── data/
│   ├── model/          # Data classes (StudentDashboard, Quiz, etc.)
│   ├── remote/         # API interface and Retrofit setup
│   └── repository/     # Data repository layer
├── ui/
│   ├── login/          # Login screen & ViewModel
│   ├── home/           # Home dashboard screen & ViewModel
│   ├── notification/   # Notifications & settings screen
│   └── theme/          # App theme and colors
└── MainActivity.kt     # App entry point with navigation
```

## 🎨 Design

- UI designed in Figma
- Follows Material Design 3 guidelines
- Custom color scheme with smooth animations
- Responsive layouts for different screen sizes

## 📊 API Integration

The app fetches student data from:
```
https://firebasestorage.googleapis.com/v0/b/user-contacts-ade83.appspot.com/o/student_dashboard.json
```

Response includes:
- Student profile (name, class, availability)
- Quiz performance (attempts, accuracy)
- Weekly overview (quiz streak, topic performance)
- Today's summary and recommendations

## 🔐 Authentication Flow

1. User enters School ID and Student ID
2. Auth0 authentication initiated
3. On success → Navigate to Dashboard
4. Logout → Clear session and return to Login

## 🏗️ Architecture

**MVVM Pattern:**
- **Model**: Data classes and repository for business logic
- **View**: Composable UI screens
- **ViewModel**: State management and data operations

**Benefits:**
- Clear separation of concerns
- Easy testing and maintenance
- Reactive UI updates with StateFlow

## 📦 Building APK

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires signing)
./gradlew assembleRelease
```

APK location: `app/build/outputs/apk/`

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project was created as an assignment submission.

## 👤 Author

**Mayan Anand**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your Profile](https://linkedin.com/in/yourprofile)

## 🙏 Acknowledgments

- Assignment provided by [Company Name]
- Auth0 for authentication services
- Figma design reference

---

⭐ Star this repo if you find it helpful!