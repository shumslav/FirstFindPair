package com.example.myapplication1.Utils

import android.content.Context
import android.content.SharedPreferences

class GameSettings(context: Context) {

    companion object {
        private const val SIZE = "GameFieldSize"
        private const val WIDTH = "Width"
        private const val HEIGHT = "Height"
        private const val SCORE = "Score"
        private const val LIVES = "Lives"
        private const val LEVEL = "Level"
        private const val CONST_GAME_LIVES = "ConstLives"
    }

    private val gameSize = context.getSharedPreferences(SIZE, Context.MODE_PRIVATE)


    var width: Int
        get() {
            return gameSize.getInt(WIDTH, -1)
        }
        set(value) {
            gameSize.edit().putInt(WIDTH, value).apply()
        }

    var height: Int
        get() {
            return gameSize.getInt(HEIGHT, -1)
        }
        set(value) {
            gameSize.edit().putInt(HEIGHT, value).apply()
        }

    var score: Int
        get() {
            return gameSize.getInt(SCORE, 0)
        }
        set(value) {
            gameSize.edit().putInt(SCORE, value).apply()
        }

    var lives: Int
        get() {
            return gameSize.getInt(LIVES, constLives)
        }
        set(value) {
            gameSize.edit().putInt(LIVES, value).apply()
        }

    var level: Int
        get() {
            return gameSize.getInt(LEVEL, 2)
        }
        set(value) {
            gameSize.edit().putInt(LEVEL, value).apply()
        }

    var constLives: Int
        get() {
            return gameSize.getInt(CONST_GAME_LIVES, 10)
        }
        set(value) {
            gameSize.edit().putInt(CONST_GAME_LIVES, value).apply()
        }


    fun addLives(livesMore: Int) {
        lives += livesMore
        constLives += livesMore
    }

}