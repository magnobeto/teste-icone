package com.sonicblue.myprojectwithleo

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

class IconManager {

    private val allAliases = listOf(
        "com.sonicblue.myprojectwithleo.DefaultIconActivity",
        "com.sonicblue.myprojectwithleo.FlaIconActivity"
        // Adicione mais aliases aqui, se tiver
    )

    fun setAppIcon(context: Context, teamIconResourceId: Int) {
        val pm = context.packageManager

        // Desativa a MainActivity
        val mainActivity = ComponentName(context, "com.sonicblue.myprojectwithleo.MainActivity")
        pm.setComponentEnabledSetting(
            mainActivity,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        // Desativa todos os aliases
        allAliases.forEach { aliasName ->
            val component = ComponentName(context, aliasName)
            pm.setComponentEnabledSetting(
                component,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }

        // Ativa somente o alias desejado
        val selectedAlias = ComponentName(
            context,
            "com.sonicblue.myprojectwithleo.${getTeamActivityName(teamIconResourceId)}"
        )
        pm.setComponentEnabledSetting(
            selectedAlias,
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
