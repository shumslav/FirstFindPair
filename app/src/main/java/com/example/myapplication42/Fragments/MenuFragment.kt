package com.example.myapplication1.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.myapplication1.R

class MenuFragment : Fragment() {

    lateinit var play: ImageView
    lateinit var shop: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        play = view.findViewById(R.id.play_menu)
        shop = view.findViewById(R.id.shop_menu)

        shop.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.alpha_one,
                    R.anim.alpha_zero,
                    R.anim.alpha_one,
                    R.anim.alpha_zero
                )
                .replace(R.id.fragment_container, ShopFragment(), null)
                .addToBackStack(null)
                .commit()
        }

        play.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.alpha_one,
                    R.anim.alpha_zero,
                    R.anim.alpha_one,
                    R.anim.alpha_zero
                )
                .replace(R.id.fragment_container, GameFragment(), null)
                .addToBackStack(null)
                .commit()
        }
        return view
    }
}