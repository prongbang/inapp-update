# inapp-update

[![](https://jitpack.io/v/prongbang/inapp-update.svg)](https://jitpack.io/#prongbang/inapp-update)

In-app updates wrapper for Android.

## Setup

- `build.gradle`

```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `settings.gradle`

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `app/build.gradle`

```groovy
implementation 'com.github.prongbang:inapp-update:1.0.0'
```

## How to use

```kotlin
import com.google.android.play.core.install.model.AppUpdateType
import com.prongbang.appupdate.AppUpdateInstallerListener
import com.prongbang.appupdate.AppUpdateInstallerManager
import com.prongbang.appupdate.InAppUpdateInstallerManager

class SplashActivity : AppCompatActivity() {

    private val appUpdateInstallerManager: AppUpdateInstallerManager by lazy {
        InAppUpdateInstallerManager(
            activity = this,
            listener = appUpdateInstallerListener,
        )
    }

    private val appUpdateInstallerListener = object : AppUpdateInstallerListener() {
        // On downloaded but not installed.
        override fun onDownloadedButNotInstalled() = popupSnackBarForCompleteUpdate()

        // On failure
        override fun onFailure(e: Exception) = navigateToMain()
        
        // On not update
        override fun onNotUpdate() = navigateToMain()

        // On cancelled update
        override fun onCancelled() = navigateToMain()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateInstallerManager.startCheckUpdate()
    }

    override fun onResume() {
        super.onResume()
        appUpdateInstallerManager.resumeCheckUpdate(AppUpdateType.FLEXIBLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        appUpdateInstallerManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    private fun popupSnackBarForCompleteUpdate() {
        val snackBar = Snackbar.make(
            findViewById(android.R.id.content),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction("RESTART") { appUpdateInstallerManager.completeUpdate() }
        snackBar.setActionTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        snackBar.show()
    }
}
```