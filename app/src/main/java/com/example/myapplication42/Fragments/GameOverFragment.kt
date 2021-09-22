package com.example.myapplication1.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication1.R
import com.example.myapplication1.Utils.GameSettings

class GameOverFragment : Fragment() {

    lateinit var gameOverFrame:RelativeLayout
    lateinit var restartGame:ImageView
    lateinit var scoreText:TextView
    lateinit var menu:ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)
        val gameSettings = GameSettings(requireContext())

        gameOverFrame = view.findViewById(R.id.game_over_frame)
        restartGame = view.findViewById(R.id.restart_game)
        scoreText = view.findViewById(R.id.score_game_over)
        menu = view.findViewById(R.id.menu_game_over)

        scoreText.text = gameSettings.score.toString()

        menu.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.alpha_one,
                    R.anim.alpha_zero,
                    R.anim.alpha_one,
                    R.anim.alpha_zero
                )
                .replace(R.id.fragment_container, MenuFragment(), null)
                .commit()
        }

        restartGame.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.alpha_one,
                    R.anim.alpha_zero,
                    R.anim.alpha_one,
                    R.anim.alpha_zero
                )
                .remove(requireParentFragment())
                .replace(R.id.fragment_container, GameFragment(), null)
                .commit()
        }

        return view
    }
}