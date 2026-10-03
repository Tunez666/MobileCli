package com.tunez.lifetree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tunez.lifetree.ui.LifeTreeApp
import com.tunez.lifetree.ui.theme.LifeTreeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LifeTreeTheme {
                LifeTreeApp()
            }
        }
    }
}