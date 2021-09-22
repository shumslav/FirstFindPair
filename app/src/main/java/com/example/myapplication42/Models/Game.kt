package com.example.myapplication1.Models

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication1.Fragments.GameFragment
import com.example.myapplication1.Fragments.GameOverFragment
import com.example.myapplication1.Fragments.MenuFragment
import com.example.myapplication1.R
import com.example.myapplication1.Utils.GameSettings
import com.example.myapplication1.Utils.images

class Game(
    private val fragment: GameFragment,
    private val context: Context,
    private val gameSize: Int,
    private val gameField: LinearLayout,
    private val livesText: TextView,
    private val scoreText: TextView,
    private var bonus: Int = 1,
) {


    private val gameSettings = GameSettings(context)
    private var firstCard: ImageView? = null
    private var secondCard: ImageView? = null
    private var allCards = mutableListOf<ImageView>()
    private var pairs = gameSize * gameSize / 2

    private val margin = when ((15 - gameSize) > 0) {
        true -> (12 - gameSize)
        else -> 0
    }

    private val cardSize = getCardSize()

    private fun getCardSize(): Int {
        val sizeFromHeight = (gameSettings.height - margin * (gameSize - 1)) / gameSize
        val sizeFromWidth = (gameSettings.width - margin * (gameSize - 1)) / gameSize
        return if (sizeFromHeight > sizeFromWidth)
            sizeFromWidth
        else
            sizeFromHeight
    }

    private fun getPairs(lastImageViews: MutableList<ImageView>) {
        val copyLastImageViews = mutableListOf<ImageView>()
        copyLastImageViews.addAll(lastImageViews)
        val pairImages = mutableListOf<Int>()
        pairImages.addAll(images)
        for (i in 0 until pairImages.size) {
            if (copyLastImageViews.size / 2 > 0) {
                var imageView = copyLastImageViews.random()
                copyLastImageViews.remove(imageView)
                val randomImage = pairImages.random()
                pairImages.remove(randomImage)
                imageView.tag = randomImage
                imageView = copyLastImageViews.random()
                copyLastImageViews.remove(imageView)
                imageView.tag = randomImage
            } else
                return
        }
        if (copyLastImageViews.size / 2 > 0)
            getPairs(copyLastImageViews)
    }

    private fun createField() {
        for (columnIndex in 0 until gameSize) {
            val column = LinearLayout(context)
            val columnParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, cardSize)
            if (columnIndex != gameSize - 1) {
                columnParams.setMargins(0, 0, 0, margin)
            }
            column.layoutParams = columnParams
            column.gravity = Gravity.CENTER_HORIZONTAL
            for (cardIndex in 0 until gameSize) {
                val card = ImageView(context)
                val cardParams = LinearLayout.LayoutParams(cardSize, cardSize)
                if (cardIndex != gameSize - 1)
                    cardParams.marginEnd = margin
                card.layoutParams = cardParams
                card.setBackgroundResource(R.drawable.close_item_)
                allCards.add(card)
                card.setOnClickListener {
                    setOnTouch(card)
                }
                column.addView(card)
            }
            gameField.addView(column)
        }
        getPairs(allCards)
    }

    fun startGame() {
        createField()
        val animAlphaOne = AnimationUtils.loadAnimation(context, R.anim.alpha_one)
        animAlphaOne.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                gameField.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        gameField.startAnimation(animAlphaOne)
    }

    private fun setOnTouch(card: ImageView) {
        if (card.tag == null) {
            allCards.remove(card)
            animateCardOpen(card)
            return
        }
        if (firstCard == null) {
            firstCard = card
            animateCardOpen(card)
        } else {
            if (secondCard == null) {
                secondCard = card
                if (checkPair()) {
                    gameSettings.score += getReward(firstCard!!.tag as Int)
                    scoreText.text = gameSettings.score.toString()
                    pairs -= 1
                    bonus += 1
                    allCards.remove(firstCard)
                    allCards.remove(secondCard)
                    animateCardFind(firstCard!!, secondCard!!)
                    firstCard = null
                    secondCard = null
                } else {
                    gameSettings.lives -= 1
                    Log.i("MyTag", gameSettings.lives.toString())
                    livesText.text = gameSettings.lives.toString()
                    bonus = 1
                    allCards.forEach { it.isClickable = false }
                    animateCardNotFind()
                }
            }
        }
    }

    private fun getReward(imageId: Int): Int {
        return when (imageId) {
            R.drawable.item_1 -> 50 * bonus
            R.drawable.item_2 -> 100 * bonus
            R.drawable.item_3 -> 125 * bonus
            R.drawable.item_4 -> 150 * bonus
            R.drawable.item_5 -> 175 * bonus
            R.drawable.item_6 -> 200 * bonus
            R.drawable.item_7 -> 225 * bonus
            R.drawable.item_8 -> 250 * bonus
            R.drawable.item_9 -> 275 * bonus
            R.drawable.item_10 -> 300 * bonus
            else -> 50 * bonus
        }

    }

    private fun animateCardOpen(card: ImageView) {
        card.animate().withLayer()
            .rotationY(90F)
            .setDuration(300)
            .withStartAction { card.isClickable = false }
            .withEndAction {
                run() {
                    card.setBackgroundResource(R.drawable.back_item_)
                    if (card.tag!=null)
                        card.setImageResource(card.tag as Int)
                    card.rotationY = -90F
                    card.animate().withLayer()
                        .rotationY(0F)
                        .setDuration(300)
                        .start()
                }
            }.start()
    }

    private fun animateCardFind(firstCard: ImageView, secondCard: ImageView) {
        val lastPairs = pairs
        secondCard.animate().withLayer()
            .rotationY(90F)
            .setDuration(300)
            .withStartAction {
                firstCard.isClickable = false
                secondCard.isClickable = false
            }
            .withEndAction {
                run() {
                    secondCard.setBackgroundResource(R.drawable.back_item_)
                    secondCard.setImageResource(secondCard.tag as Int)
                    secondCard.rotationY = -90F
                    secondCard.animate().withLayer()
                        .rotationY(0F)
                        .setDuration(300)
                        .withEndAction {
                            if (lastPairs == 0) {
                                gameSettings.level += 1
                                nextLevel()
                            }
                        }
                        .start()
                }
            }.start()
    }

    private fun animateCardNotFind() {
        secondCard!!.animate().withLayer()
            .rotationY(90F)
            .setDuration(300)
            .withStartAction { allCards.forEach { it.isClickable = false } }
            .withEndAction {
                run() {
                    secondCard!!.setBackgroundResource(R.drawable.back_item_)
                    secondCard!!.setImageResource(secondCard!!.tag as Int)
                    secondCard!!.rotationY = -90F
                    secondCard!!.animate().withLayer()
                        .rotationY(0F)
                        .setDuration(200)
                        .withEndAction {
                            firstCard!!.animate().withLayer()
                                .rotationY(-90F)
                                .setDuration(200)
                                .withEndAction {
                                    run() {
                                        firstCard!!.setBackgroundResource(R.drawable.close_item_)
                                        firstCard!!.setImageDrawable(null)
                                        firstCard!!.rotationY = 90F
                                        firstCard!!.animate().withLayer()
                                            .rotationY(0F)
                                            .setDuration(200)
                                            .start()
                                    }
                                }.start()
                            secondCard!!.animate().withLayer()
                                .rotationY(-90F)
                                .setDuration(200)
                                .withEndAction {
                                    run() {
                                        secondCard!!.setBackgroundResource(R.drawable.close_item_)
                                        secondCard!!.setImageDrawable(null)
                                        secondCard!!.rotationY = 90F
                                        secondCard!!.animate().withLayer()
                                            .rotationY(0F)
                                            .setDuration(200)
                                            .withEndAction {
                                                if (gameSettings.lives == 0)
                                                    finishGame()
                                                else {
                                                    allCards.forEach {
                                                        it.isClickable = true
                                                    }
                                                    firstCard = null
                                                    secondCard = null
                                                }
                                            }
                                            .start()
                                    }
                                }.start()
                        }
                        .start()
                }
            }.start()
    }

    private fun finishGame() {
        allCards.forEach { it.isClickable = false }
        gameSettings.lives = gameSettings.constLives
        gameSettings.level = 2
        fragment.childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.alpha_one,
                R.anim.alpha_zero,
                R.anim.alpha_one,
                R.anim.alpha_zero
            )
            .add(R.id.result_container, GameOverFragment(), null)
            .commit()
    }

    private fun checkPair(): Boolean {
        return ((firstCard!!.tag as Int) == (secondCard!!.tag as Int))
    }

    private fun nextLevel() {
        val game = Game(
            fragment,
            context,
            gameSettings.level,
            gameField,
            livesText,
            scoreText,
            bonus = bonus
        )
        val animAlphaZero = AnimationUtils.loadAnimation(context, R.anim.alpha_zero)
        val listener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                gameField.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                gameField.removeAllViews()
                game.startGame()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        }
        animAlphaZero.setAnimationListener(listener)
        gameField.startAnimation(animAlphaZero)
    }
}