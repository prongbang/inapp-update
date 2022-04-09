package com.prongbang.appupdate

import com.google.android.play.core.install.model.InstallStatus

abstract class AppUpdateInstallerListener {
    fun onStatus(@InstallStatus status: Int) {}
    abstract fun onDownloadedButNotInstalled()
    abstract fun onFailure(e: Exception)
    abstract fun onNotUpdate()
    abstract fun onCancelled()
}