package com.sonicblue.myprojectwithleo

import android.content.Context

object IconPersistence {
    private const val PREFS_NAME = "IconPrefs"
    private const val KEY_ICON_ALIAS = "selected_icon_alias"

    fun saveIconAlias(context: Context, alias: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_ICON_ALIAS, alias).apply()
    }

    fun getSavedIconAlias(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        // Retorna null se nada for encontrado, assim sabemos que é a primeira vez
        return prefs.getString(KEY_ICON_ALIAS, null)
    }
}