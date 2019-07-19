package com.jeongwoochang.sunrinhackathon5th.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.jeongwoochang.sunrinhackathon5th.MyApplication
import com.jeongwoochang.sunrinhackathon5th.R
import com.jeongwoochang.sunrinhackathon5th.util.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.getInstance().setOnVisibilityChangeListener {
            if (it) {
                if (!SharedPreferencesHelper(this).autoLogin)
                    SharedPreferencesHelper(this).removeCookies()
            }
        }
    }
}