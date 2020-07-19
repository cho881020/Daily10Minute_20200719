package kr.co.tjoeun.daily10minute_20200719.utils

import android.content.Context

class ContextUtil {

    companion object {

        private val prefName = "daily10minutePref"

        private val LOGIN_USER_TOKEN = "LOGIN_USER_TOKEN"

        fun setLoginUserToken(context: Context, token:String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_USER_TOKEN, token).apply()
        }

        fun getLoginUserToken(context: Context) : String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_USER_TOKEN, "")!!
        }

    }

}