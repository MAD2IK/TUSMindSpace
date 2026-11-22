package com.deepudinesh.tusmindspace.data

import android.content.Context

object ProfilePrefs {
    private const val PREFS = "profile_prefs"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun name(context: Context) = prefs(context).getString("name", "") ?: ""
    fun course(context: Context) = prefs(context).getString("course", "") ?: ""
    fun year(context: Context) = prefs(context).getString("year", "") ?: ""
    fun save(context: Context, name: String, course: String, year: String) {
        prefs(context).edit()
            .putString("name", name)
            .putString("course", course)
            .putString("year", year)
            .apply()
    }
    fun isSignedIn(context: Context) = prefs(context).getBoolean("signed_in", false)
    fun signIn(context: Context, name: String) {
        prefs(context).edit()
            .putBoolean("signed_in", true)
            .putString("name", name)
            .apply()
    }
}