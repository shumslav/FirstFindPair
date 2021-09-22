package com.example.myapplication1.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication1.R
import com.example.myapplication1.Utils.GameSettings
import com.example.myapplication1.Utils.ShopSettings

class ShopFragment : Fragment() {

    lateinit var twoLives: ImageView
    lateinit var threeLives: ImageView
    lateinit var fiveLives: ImageView
    lateinit var back: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        val shopSettings = ShopSettings(requireContext())
        val gameSettings = GameSettings(requireContext())

        twoLives = view.findViewById(R.id.shop_two_lives)
        threeLives = view.findViewById(R.id.shop_three_lives)
        fiveLives = view.findViewById(R.id.shop_five_lives)
        back = view.findViewById(R.id.back_shop)

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        if (shopSettings.isBuyTwoLives) {
            twoLives.visibility = View.INVISIBLE
        } else {
            twoLives.setOnClickListener {
                if (gameSettings.score >= 3000) {
                    gameSettings.score -= 3000
                    shopSettings.buyTwoLives()
                    gameSettings.addLives(2)
                    val animationAlpha =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_zero)
                    animationAlpha.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                            twoLives.isClickable = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            twoLives.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                    twoLives.startAnimation(animationAlpha)
                } else {
                    Toast.makeText(requireContext(), "Not enough coins", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (shopSettings.isBuyThreeLives) {
            threeLives.visibility = View.INVISIBLE
        } else {
            threeLives.setOnClickListener {
                if (gameSettings.score >= 10000) {
                    gameSettings.score -= 10000
                    shopSettings.buyThreeLives()
                    gameSettings.addLives(3)
                    val animationAlpha =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_zero)
                    animationAlpha.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                            threeLives.isClickable = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            threeLives.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                    threeLives.startAnimation(animationAlpha)
                } else {
                    Toast.makeText(requireContext(), "Not enough coins", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (shopSettings.isBuyFiveLives) {
            fiveLives.visibility = View.INVISIBLE
        } else {

            fiveLives.setOnClickListener {
                if (gameSettings.score >= 25000) {
                    gameSettings.score -= 25000
                    shopSettings.buyFiveLives()
                    gameSettings.addLives(5)
                    val animationAlpha =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_zero)
                    animationAlpha.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                            fiveLives.isClickable = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            fiveLives.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                    fiveLives.startAnimation(animationAlpha)
                } else {
                    Toast.makeText(requireContext(), "Not enough coins", Toast.LENGTH_SHORT).show()
                }
            }

        }

        return view
    }
}