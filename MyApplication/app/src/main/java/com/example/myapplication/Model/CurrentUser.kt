package com.example.myapplication.Model

import android.content.Context
import android.content.SharedPreferences

class CurrentUser()
{
    companion object {

        lateinit var sharedPreferences: SharedPreferences
        private var FIRST_NAME = "firstName"
        private var LAST_NAME = "lastName"
        private var EMAIL = "email"
        private var JWT = "token"

        fun initializeCurrentUser(context: Context) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            }
        }

        fun writeSharedPref(user: User) {
            val editor = sharedPreferences.edit()
            editor.putString(FIRST_NAME, user.firstName)
            editor.putString(LAST_NAME, user.lastName)
            editor.putString(EMAIL, user.email)
            editor.putString(JWT, user.jwt)
            editor.apply()
        }
    }
}