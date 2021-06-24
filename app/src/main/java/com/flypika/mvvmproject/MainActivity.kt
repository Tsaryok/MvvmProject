package com.flypika.mvvmproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flypika.mvvmproject.ui.NewsListFragment

//Ctrl + Alt + L

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null){
            // для каждого действия в билдер-лайк паттернах
            // лучше отдельную строку заводить
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, NewsListFragment()).commit()
        }
    }
}