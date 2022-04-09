package com.prongbang.update

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.prongbang.appupdate.AppUpdateInstallerListener
import com.prongbang.appupdate.AppUpdateInstallerManager
import com.prongbang.appupdate.InAppUpdateInstallerManager

class SplashActivity : AppCompatActivity() {

    private val appUpdateInstallerManager: AppUpdateInstallerManager by lazy {
        InAppUpdateInstallerManager(this)
    }

    private val appUpdateInstallerListener by lazy {
        object : AppUpdateInstallerListener() {
            override fun onDownloadedButNotInstalled() = popupSnackBarForCompleteUpdate()

            override fun onFailure(e: Exception) = navigateToMain()

            override fun onNotUpdate() = navigateToMain()

            override fun onCancelled() = navigateToMain()

            override fun onStatus(@InstallStatus status: Int) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateInstallerManager.addAppUpdateListener(appUpdateInstallerListener)
        appUpdateInstallerManager.startCheckUpdate()
    }

    override fun onResume() {
        super.onResume()
        appUpdateInstallerManager.resumeCheckUpdate(AppUpdateType.IMMEDIATE)
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