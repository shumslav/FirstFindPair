package com.example.myapplication1.Utils

import android.content.Context

class ShopSettings(context: Context) {
    companion object{
        private const val SETTINGS = "ShopSettings"
        private const val TWO_LIVES = "TwoLives"
        private const val THREE_LIVES = "ThreeLives"
        private const val FIVE_LIVES = "FiveLives"
    }

    private val shopSettings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

    val isBuyTwoLives:Boolean
    get() {return shopSettings.getBoolean(TWO_LIVES,false)}

    val isBuyThreeLives:Boolean
        get() {return shopSettings.getBoolean(THREE_LIVES,false)}

    val isBuyFiveLives:Boolean
        get() {return shopSettings.getBoolean(FIVE_LIVES,false)}

    fun buyTwoLives(){
        shopSettings.edit().putBoolean(TWO_LIVES,true).apply()
    }

    fun buyThreeLives(){
        shopSettings.edit().putBoolean(THREE_LIVES,true).apply()
    }

    fun buyFiveLives(){
        shopSettings.edit().putBoolean(FIVE_LIVES,true).apply()
    }
}