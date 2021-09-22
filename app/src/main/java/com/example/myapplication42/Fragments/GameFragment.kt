package com.example.myapplication1.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import com.example.myapplication1.Models.Game
import com.example.myapplication1.R
import com.example.myapplication1.Utils.GameSettings

class GameFragment : Fragment() {

    lateinit var scoreText:TextView
    lateinit var livesText:TextView
    lateinit var menu:ImageView

    lateinit var board:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val gameSettings = GameSettings(requireContext())

        livesText = view.findViewById(R.id.lives)
        scoreText = view.findViewById(R.id.balance_text)
        menu = view.findViewById(R.id.menu_game)

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

        livesText.text = gameSettings.lives.toString()
        scoreText.text = gameSettings.score.toString()

        board = view.findViewById(R.id.board)

        board.doOnPreDraw {
            if (gameSettings.width==-1){
                gameSettings.width = board.width
                gameSettings.height = board.height
            }
            val game = Game(this,requireContext(),gameSettings.level,board,livesText,scoreText)
            game.startGame()
        }

        return view
    }
}