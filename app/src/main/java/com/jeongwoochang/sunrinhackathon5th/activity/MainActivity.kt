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

    var drawerToggle: ActionBarDrawerToggle? = null
    var drawer_str = arrayOf("mypage", "setup", "help")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, drawer_str)
        drawer.adapter = adapter

        drawerToggle = object : ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }
        drawerlayout.addDrawerListener(drawerToggle as ActionBarDrawerToggle)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        MyApplication.getInstance().setOnVisibilityChangeListener {
            if (it) {
                if (!SharedPreferencesHelper(this).autoLogin)
                    SharedPreferencesHelper(this).removeCookies()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle!!.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }
}
