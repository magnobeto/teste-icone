package com.sonicblue.myprojectwithleo

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

class IconManager {
    fun setAppIcon(context: Context, teamIconResourceId: Int) {
        val componentName = ComponentName(
            context.packageName,
            "com.sonicblue.myprojectwithleo.MainActivity"
        )

        val packageManager = context.packageManager
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        val teamIconComponent = ComponentName(
            context.packageName,
            "com.sonicblue.myprojectwithleo.${getTeamActivityName(teamIconResourceId)}"
        )
        packageManager.setComponentEnabledSetting(
            teamIconComponent,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun getTeamActivityName(teamIconResourceId: Int): String {
        return when (teamIconResourceId) {
            R.drawable.ic_dev_tranquilao -> "FlaIconActivity"
            else -> "DefaultIconActivity"
        }
    }
}