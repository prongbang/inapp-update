package com.prongbang.appupdate

import android.content.Intent
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.tasks.Task

interface AppUpdateInstallerManager {
    fun startCheckUpdate()
    fun resumeCheckUpdate(@AppUpdateType updateType: Int)
    fun completeUpdate(): Task<Void>
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}