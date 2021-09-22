package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.myapplication1.Fragments.GameFragment
import com.example.myapplication1.Fragments.MenuFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.alpha_one,
                R.anim.alpha_zero,
                R.anim.alpha_one,
                R.anim.alpha_zero
            )
            .replace(R.id.fragment_container, MenuFragment(), null)
            .commit()
    }

    override fun onBackPressed() {
        Log.i("TAG", supportFragmentManager.fragments.size.toString())
        if (supportFragmentManager.backStackEntryCount!=0) {
            supportFragmentManager.popBackStack()
        }
        else
            super.onBackPressed()
    }
}