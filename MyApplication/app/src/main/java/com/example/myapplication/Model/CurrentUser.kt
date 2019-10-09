package com.example.myapplication.Model

import android.content.Context
import android.content.SharedPreferences

class CurrentUser()
{
    companion object {

        private lateinit var sharedPreferences: SharedPreferences
        private var FIRST_NAME = "firstName"
        private var LAST_NAME = "lastName"
        private var EMAIL = "email"
        private var JWT = "token"

        fun initializeCurrentUser(context: Context) {
                sharedPreferences = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)

        }

        fun writeSharedPref(user: User) {
            val editor = sharedPreferences.edit()
            editor.putString(FIRST_NAME, user.firstName)
            editor.putString(LAST_NAME, user.lastName)
            editor.putString(EMAIL, user.email)
            editor.putString(JWT, user.jwt)
            editor.apply()
        }

        fun getFirstName() : String?{
            return sharedPreferences.getString(FIRST_NAME, "")
        }

        fun getLastName() : String?{
            return sharedPreferences.getString(LAST_NAME, "")
        }

        fun getEmail() : String?{
            return sharedPreferences.getString(EMAIL, "")
        }

        fun getJWT() : String?{
            return sharedPreferences.getString(JWT, "")
        }
    }
}