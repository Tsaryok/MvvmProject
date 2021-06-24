package com.flypika.mvvmproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flypika.mvvmproject.ui.NewsListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, NewsListFragment())
                .commit()
        }
    }
}