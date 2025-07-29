package com.sonicblue.myprojectwithleo

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class IconManager {

    fun setAppIcon(context: Context, aliasFullName: String) {
        val pm = context.packageManager
        val packageName = context.packageName

        val allAliases = listOf(
            "$packageName.DefaultIconActivity",
            "$packageName.FlaIconActivity"
        )

        // Atrasamos a execução em 300ms.
        // Isso dá tempo para o evento de clique ser processado e evita o fechamento imediato.
        Handler(Looper.getMainLooper()).postDelayed({
            allAliases.forEach { alias ->
                val state = if (alias == aliasFullName) {
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                } else {
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                }
                pm.setComponentEnabledSetting(
                    ComponentName(context, alias),
                    state,
                    PackageManager.DONT_KILL_APP
                )
            }
            // Informa ao usuário que a mudança pode levar um momento.
            Toast.makeText(context, "Ícone do app será atualizado.", Toast.LENGTH_SHORT).show()
        }, 300) // 300 milissegundos de atraso
    }
}